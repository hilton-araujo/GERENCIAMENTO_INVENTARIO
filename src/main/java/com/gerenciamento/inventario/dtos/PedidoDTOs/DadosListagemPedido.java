package com.gerenciamento.inventario.dtos.PedidoDTOs;

import com.gerenciamento.inventario.models.Cliente;
import com.gerenciamento.inventario.models.Produto;

public record DadosListagemPedido (

        Cliente cliente,

        Produto produto
){
}
