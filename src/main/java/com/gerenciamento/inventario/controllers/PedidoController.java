package com.gerenciamento.inventario.controllers;

import com.gerenciamento.inventario.dtos.PedidoDTOs.DadosCadastroPedidoDTO;
import com.gerenciamento.inventario.dtos.PedidoDTOs.DadosListagemPedido;
import com.gerenciamento.inventario.dtos.Response.ResponseApi;
import com.gerenciamento.inventario.models.Cliente;
import com.gerenciamento.inventario.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity<ResponseApi> cadastrar(@RequestBody DadosCadastroPedidoDTO dto){
        try {
            service.registrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseApi("Pedido realizado com sucesso", null));
        }catch (ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ResponseApi(e.getReason(), null));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi("Erro ao realizar pedido", null));
        }
    }

    @GetMapping(value = "/{nuit}")
    public ResponseEntity<ResponseApi> listarTodos(@PathVariable String nuit) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseApi("Todos Pedidos do Clientes no sistema", service.listar(nuit)));
    }
}
