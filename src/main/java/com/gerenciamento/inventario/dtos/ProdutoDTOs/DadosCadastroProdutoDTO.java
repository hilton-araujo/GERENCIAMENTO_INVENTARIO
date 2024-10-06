package com.gerenciamento.inventario.dtos.ProdutoDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroProdutoDTO(

        @NotBlank(message = "O nome do produto não pode ser nulo")
        String name,

        String code,

        String codigoBarras,

        @NotNull(message = "A quantidade do produto não pode ser nulo")
        int qtd,

        String descricao,

        String categoriaId
) {
}
