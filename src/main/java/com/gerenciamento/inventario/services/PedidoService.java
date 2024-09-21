package com.gerenciamento.inventario.services;

import com.gerenciamento.inventario.dtos.PedidoDTOs.DadosCadastroPedidoDTO;
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


    public Pedido create(Pedido pedido){
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

            double precoUnitario = Double.parseDouble(precoProduto.getPreco());
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


}
