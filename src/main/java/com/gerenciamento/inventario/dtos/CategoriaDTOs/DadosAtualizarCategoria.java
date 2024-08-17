package com.gerenciamento.inventario.dtos.CategoriaDTOs;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizarCategoria(

        @NotBlank(message = "O id da categoria não pode ser nulo")
        String id,

        @NotBlank(message = "O nome da categoria não pode ser nulo")
        String name
) {
}
