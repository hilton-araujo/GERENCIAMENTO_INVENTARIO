package com.gerenciamento.inventario.controllers;

import com.gerenciamento.inventario.dtos.ClienteDTOs.DadosCadastroCliente;
import com.gerenciamento.inventario.dtos.ProdutoDTOs.DadosCadastroProdutoDTO;
import com.gerenciamento.inventario.dtos.Response.ResponseApi;
import com.gerenciamento.inventario.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<ResponseApi> cadastrar(@RequestBody @Valid DadosCadastroCliente dto) {
        try {
            service.registrarCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Cliente adicionado com sucesso!", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao adicionar cliente", null));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseApi> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos Clientes do sistema", service.listar()));
    }
}
