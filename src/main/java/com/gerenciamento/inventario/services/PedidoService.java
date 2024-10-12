package com.gerenciamento.inventario.services;

import com.gerenciamento.inventario.dtos.PedidoDTOs.DadosCadastroPedidoDTO;
import com.gerenciamento.inventario.dtos.PedidoDTOs.DadosListagemPedido;
import com.gerenciamento.inventario.models.Cliente;
import com.gerenciamento.inventario.models.Pedido;
import com.gerenciamento.inventario.models.PrecoProduto;
import com.gerenciamento.inventario.models.Produto;
import com.gerenciamento.inventario.respositories.ClienteRespository;
import com.gerenciamento.inventario.respositories.PedidoRepository;
import com.gerenciamento.inventario.respositories.PrecoProdutoRepository;
import com.gerenciamento.inventario.respositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ClienteRespository clienteRespository;

    @Autowired
    private PrecoProdutoRepository precoProdutoRepository;


    public Pedido create(Pedido pedido) {
        return repository.save(pedido);
    }

    public void registrar(DadosCadastroPedidoDTO cadastroPedidoDTO) {
        try {
            Cliente cliente = clienteRespository.findByNuit(cadastroPedidoDTO.clientNuit())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

            Produto produto = produtoRepository.findById(cadastroPedidoDTO.produtoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            PrecoProduto precoProduto = precoProdutoRepository.findByProdutoId(cadastroPedidoDTO.produtoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto não contém preço"));

            int quantidadeDisponivel = produto.getQtd();
            int quantidadeSolicitada = cadastroPedidoDTO.qtd();

            if (quantidadeSolicitada > quantidadeDisponivel) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade solicitada excede a quantidade disponível");
            }

            double precoUnitario;
            try {
                precoUnitario = Double.parseDouble(precoProduto.getPreco());
            } catch (NumberFormatException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preço inválido para o produto");
            }

            double valorTotal = precoUnitario * quantidadeSolicitada;

            produto.setQtd(quantidadeDisponivel - quantidadeSolicitada);
            produtoRepository.save(produto);

            Pedido pedido = new Pedido();
            pedido.setClientNuit(cliente);
            pedido.setProductId(precoProduto);
            pedido.setQtd(quantidadeSolicitada);
            pedido.setValorTotal(valorTotal);

            create(pedido);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar o pedido", e);
        }
    }

    public List<DadosListagemPedido> listar(String nuit) {
        Cliente cliente = clienteRespository.findByNuit(nuit)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        List<Pedido> pedidos = repository.findByClientNuit(cliente);
        if (pedidos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum pedido encontrado para este cliente");
        }

        List<DadosListagemPedido> list = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            // Supondo que `pedido.getProduto()` retorne um objeto do tipo Produto ou PrecoProduto
            String nomeProduto = pedido.getProductId().getProduto().getName();
            String precoUnitario = pedido.getProductId().getPreco(); // Extrair o nome do produto

            DadosListagemPedido dadosListagemPedido = new DadosListagemPedido(
                    nomeProduto,
                    precoUnitario,// Passa o nome do produto
                    pedido.getQtd(),
                    pedido.getValorTotal()
            );
            list.add(dadosListagemPedido);
        }
        return list;
    }

}
