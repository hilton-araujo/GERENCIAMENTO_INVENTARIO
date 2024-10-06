package com.gerenciamento.inventario.services;

import com.gerenciamento.inventario.dtos.ProdutoDTOs.DadosAtualizarProdutoDTO;
import com.gerenciamento.inventario.dtos.ProdutoDTOs.DadosCadastroProdutoDTO;
import com.gerenciamento.inventario.dtos.ProdutoDTOs.DadosVisualizacaoProdutoDTO;
import com.gerenciamento.inventario.funcoes.GerarCodigos;
import com.gerenciamento.inventario.models.Categoria;
import com.gerenciamento.inventario.models.Produto;
import com.gerenciamento.inventario.models.ProdutoSpecification;
import com.gerenciamento.inventario.respositories.CategoriaRepository;
import com.gerenciamento.inventario.respositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findById(String id) throws ChangeSetPersister.NotFoundException {
        return  repository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    private String gerarCodigo() {
        String codigo;
        do {
            codigo = GerarCodigos.gerarCodigoSeteDigitos();
        } while (repository.existsByCodigo(codigo));
        return codigo;
    }

    public Produto cadastrar(Produto produto) {
        if (produto.getCodigo() == null || produto.getCodigo().isEmpty()) {
            produto.setCodigo(gerarCodigo());
        }

        if (produto.getCodigoBarras() == null || produto.getCodigoBarras().isEmpty()) {
            produto.setCodigoBarras(GerarCodigos.gerarCodigoBarras());
        }
        return repository.save(produto);
    }

    public void registrar(DadosCadastroProdutoDTO dadosCadastroProdutoDTO) {
        try {
            Produto produto;

            Categoria categoria = categoriaRepository.findById(dadosCadastroProdutoDTO.categoriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrado"));

            boolean produtoNomeExiste = repository.existsByName(dadosCadastroProdutoDTO.name());
            if (produtoNomeExiste) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto com o mesmo nome já existe");
            }


            produto = new Produto();
            produto.setName(dadosCadastroProdutoDTO.name());
            produto.setQtd(dadosCadastroProdutoDTO.qtd());
            produto.setDescricao(dadosCadastroProdutoDTO.descricao());
            produto.setCategoria(categoria);
            produto.setAtivo(true);

            cadastrar(produto);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar produto", e);
        }
    }

    public void atualizar(DadosAtualizarProdutoDTO dto) {
        try {
            Produto produto = repository.findById(dto.id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));


            if (!produto.getName().equals(dto.name())) {
                boolean produtoNomeExiste = repository.existsByNameAndIdNot(dto.name(), dto.id());
                if (produtoNomeExiste) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto com o mesmo nome já existe");
                }
            }

            produto.setName(dto.name());
            produto.setQtd(dto.qtd());
            produto.setDescricao(dto.descricao());
            produto.setCategoria(categoria);

            this.cadastrar(produto);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar produto", e);
        }
    }

    public List<DadosVisualizacaoProdutoDTO> listar(){
        List<Produto> produtos = repository.findAll();
        List<DadosVisualizacaoProdutoDTO> dtos = new ArrayList<>();

        for (Produto produto : produtos) {
            DadosVisualizacaoProdutoDTO dto = new DadosVisualizacaoProdutoDTO(
                    produto.getId(),
                    produto.getName(),
                    produto.getQtd(),
                    produto.getDescricao(),
                    produto.getCodigoBarras(),
                    produto.getCodigo(),
                    produto.getAtivo(),
                    produto.getCategoria().getName()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public DadosVisualizacaoProdutoDTO listarPorId(String id) throws ChangeSetPersister.NotFoundException {
        Produto produto = this.findById(id);

        return new DadosVisualizacaoProdutoDTO(
                produto.getId(),
                produto.getName(),
                produto.getQtd(),
                produto.getDescricao(),
                produto.getCodigoBarras(),
                produto.getCodigo(),
                produto.getAtivo(),
                produto.getCategoria().getName()
        );
    }

    public void inativarProduto(String id) {
        try {
            Produto produto = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            produto.setAtivo(false);
            cadastrar(produto);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao inativar produto", e);
        }
    }

    public void ativar(String id) {
        try {
            Produto produto = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            produto.setAtivo(true);
            cadastrar(produto);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao inativar produto", e);
        }
    }
}
