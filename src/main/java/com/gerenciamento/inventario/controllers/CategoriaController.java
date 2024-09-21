package com.gerenciamento.inventario.controllers;

import com.gerenciamento.inventario.dtos.CategoriaDTOs.DadosAtualizarCategoria;
import com.gerenciamento.inventario.dtos.CategoriaDTOs.DadosCadastroCategoriaDTO;
import com.gerenciamento.inventario.dtos.Response.ResponseApi;
import com.gerenciamento.inventario.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    public ResponseEntity<ResponseApi> cadastrar(@RequestBody @Valid DadosCadastroCategoriaDTO dto){
        try {
            service.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Categoria cadastrado com sucesso!", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar categoria", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listar() throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todas Categorias do sistema", service.listar()));
    }

    @PutMapping
    public ResponseEntity<ResponseApi> atualizar(@RequestBody @Valid DadosAtualizarCategoria dto){
        try {
            service.atualizarCategoria(dto);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Categoria atualizada com sucesso", null));
        }catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao atualizar categoria", null));
        }
    }
}
