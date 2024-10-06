package com.gerenciamento.inventario.dtos.PedidoDTOs;

import com.gerenciamento.inventario.models.Cliente;
import com.gerenciamento.inventario.models.Pedido;
import com.gerenciamento.inventario.models.PrecoProduto;
import com.gerenciamento.inventario.models.Produto;

public record DadosListagemPedido (

        Cliente cliente,

        PrecoProduto produto
){
}
