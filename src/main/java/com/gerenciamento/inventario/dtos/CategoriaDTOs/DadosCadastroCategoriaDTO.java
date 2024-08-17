package com.gerenciamento.inventario.dtos.CategoriaDTOs;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCategoriaDTO (

        @NotBlank(message = "O nome da categoria não pode ser nulo")
        String name
) {
}
