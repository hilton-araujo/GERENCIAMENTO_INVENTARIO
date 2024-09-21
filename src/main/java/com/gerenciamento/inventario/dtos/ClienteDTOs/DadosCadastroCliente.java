package com.gerenciamento.inventario.dtos.ClienteDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroCliente(

        @NotBlank(message = "O nome não pode ser nulo")
        String nome,

        @NotBlank(message = "O email não pode ser nulo")
        @Email(message = "O email não pode ser nulo")
        String email,

        @NotBlank(message = "O nuit não pode ser nulo")
        @Size(min = 9, max = 9, message = "O nuit deve ter exatamente 9 caracteres")
        String nuit
) {
}
