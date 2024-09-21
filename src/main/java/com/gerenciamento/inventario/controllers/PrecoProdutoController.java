package com.gerenciamento.inventario.controllers;

import com.gerenciamento.inventario.dtos.PrecoProdutoDTOs.DadosCadastroPrecoProdutoDTO;
import com.gerenciamento.inventario.dtos.Response.ResponseApi;
import com.gerenciamento.inventario.models.PrecoProduto;
import com.gerenciamento.inventario.services.PrecoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/preco")
public class PrecoProdutoController {

    @Autowired
    private PrecoProdutoService service;

    @PostMapping
    public ResponseEntity<ResponseApi> cadastrar(@RequestBody DadosCadastroPrecoProdutoDTO dto){
        try {
            service.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Preco produto cadastrado com sucesso", null));
        }catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao cadastrar preco de produto", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listarPrecos(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos produtos com precos", service.listar()));
    }
}
