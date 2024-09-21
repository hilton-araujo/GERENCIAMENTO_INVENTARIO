package com.gerenciamento.inventario.controllers;

import com.gerenciamento.inventario.dtos.ProdutoDTOs.DadosAtualizarProdutoDTO;
import com.gerenciamento.inventario.dtos.ProdutoDTOs.DadosCadastroProdutoDTO;
import com.gerenciamento.inventario.dtos.ProdutoDTOs.DadosVisualizacaoProdutoDTO;
import com.gerenciamento.inventario.dtos.Response.ResponseApi;
import com.gerenciamento.inventario.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<ResponseApi> cadastrar(@RequestBody @Valid DadosCadastroProdutoDTO dto) {
        try {
            service.registrar(dto);
            System.out.println(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Produto cadastrado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar produto", null));
        }
    }

//    @GetMapping
//    public ResponseEntity<ResponseApi> listar(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) Integer qtd,
//            @RequestParam(required = false) String categoriaId,
//            @RequestParam(required = false) String descricao,
//            @RequestParam(required = false) Boolean ativo,
//            Pageable pageable
//    ) {
////        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos Produtos do sistema", service.listar()));
//        try {
//            Page<DadosVisualizacaoProdutoDTO> produtos = service.listar();
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Produtos encontrados", produtos));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao buscar produtos", null));
//        }
//    }

    @GetMapping
    public ResponseEntity<ResponseApi> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos Produtos do sistema", service.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> listarId(@PathVariable String id) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Produto com id "+id, service.listarPorId(id)));
    }

    @PutMapping
    public ResponseEntity<ResponseApi> atualizar(@RequestBody @Valid DadosAtualizarProdutoDTO dto){
        try {
            service.atualizar(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Produto atualizado com sucesso", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao atualizar produto", null));
        }
    }

    @DeleteMapping("/inativar/{id}")
    public ResponseEntity<ResponseApi> inativar(@PathVariable String id) {
        try{
            service.inativarProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Produto inativado com sucesso", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao inativar produto", null));
        }
    }

    @PatchMapping("/ativar/{id}")
    public ResponseEntity<ResponseApi> ativar(@PathVariable String id) {
        try{
            service.ativar(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Produto ativado com sucesso", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao ativar produto", null));
        }
    }
}
