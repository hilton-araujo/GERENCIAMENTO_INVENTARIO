package com.gerenciamento.inventario.dtos.ClienteDTOs;

public record DadosAtualizacaoCliente(
        String id,

        String nome,

        String email,

        String nuit
) {
}
