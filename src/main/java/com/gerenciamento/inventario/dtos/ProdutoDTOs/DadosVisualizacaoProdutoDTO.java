package com.gerenciamento.inventario.dtos.ProdutoDTOs;

public record DadosVisualizacaoProdutoDTO (

        String id,

        String name,

        int qtd,

        String descricao,

        String codigoBarras,

        String codigo,

        Boolean ativo,

        String categoria
){
}
