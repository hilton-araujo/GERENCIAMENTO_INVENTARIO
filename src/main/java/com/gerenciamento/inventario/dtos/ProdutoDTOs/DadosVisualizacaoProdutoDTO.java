package com.gerenciamento.inventario.dtos.ProdutoDTOs;

public record DadosVisualizacaoProdutoDTO (

        String id,

        String name,

        int qtd,

        String descricao,

        Boolean ativo,

        String categoria
){
}
