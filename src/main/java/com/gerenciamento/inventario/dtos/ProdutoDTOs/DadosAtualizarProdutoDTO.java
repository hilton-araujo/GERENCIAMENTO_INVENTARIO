package com.gerenciamento.inventario.dtos.ProdutoDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarProdutoDTO(

        String id,

        @NotBlank(message = "O nome do produto não pode ser nulo")
        String name,

        @NotNull(message = "A quantidade do produto não pode ser nulo")
        int qtd,

        String descricao,

        boolean ativo,

        @NotBlank(message = "A categoria do produto não pode ser nulo")
        String categoriaId
) {
}
