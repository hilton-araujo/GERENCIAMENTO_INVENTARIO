package com.gerenciamento.inventario.services;

import com.gerenciamento.inventario.dtos.PrecoProdutoDTOs.DadosCadastroPrecoProdutoDTO;
import com.gerenciamento.inventario.dtos.PrecoProdutoDTOs.DadosLIstagemProdutoPrecoDTO;
import com.gerenciamento.inventario.models.Categoria;
import com.gerenciamento.inventario.models.PrecoProduto;
import com.gerenciamento.inventario.models.Produto;
import com.gerenciamento.inventario.respositories.PrecoProdutoRepository;
import com.gerenciamento.inventario.respositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrecoProdutoService {

    @Autowired
    private PrecoProdutoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public PrecoProduto create(PrecoProduto produto){
        return repository.save(produto);
    }

    public void cadastrar(DadosCadastroPrecoProdutoDTO dto){
        try {
            PrecoProduto precoProduto;
            Produto produto = produtoRepository.findById(dto.produtoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            boolean alreadyExistsByProduto = repository.existsByProduto(produto);

            if (alreadyExistsByProduto){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto já tem preco");
            }

            precoProduto = new PrecoProduto();
            precoProduto.setPreco(dto.preco());
            precoProduto.setProduto(produto);

            create(precoProduto);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar preco de produto", e);
        }
    }

    public List<DadosLIstagemProdutoPrecoDTO> listar(){
        List<PrecoProduto> precoProdutos = repository.findAll();

        List<DadosLIstagemProdutoPrecoDTO> dtos = new ArrayList<>();

        for (PrecoProduto precoProduto : precoProdutos) {
            DadosLIstagemProdutoPrecoDTO lIstagemProdutoPrecoDTO = new DadosLIstagemProdutoPrecoDTO(
                    precoProduto.getId(),
                    precoProduto.getPreco(),
                    precoProduto.getProduto()
            );
            dtos.add(lIstagemProdutoPrecoDTO);
        }
        return dtos;
    }
}
