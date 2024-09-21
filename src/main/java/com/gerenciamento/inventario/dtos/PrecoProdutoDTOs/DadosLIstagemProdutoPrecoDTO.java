package com.gerenciamento.inventario.dtos.PrecoProdutoDTOs;

import com.gerenciamento.inventario.models.Produto;

public record DadosLIstagemProdutoPrecoDTO(

        String id,


        String preco,
        Produto produto

        ) {
}
