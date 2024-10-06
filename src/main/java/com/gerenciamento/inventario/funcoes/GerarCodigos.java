package com.gerenciamento.inventario.funcoes;

import java.util.Random;
import java.util.UUID;

public class GerarCodigos {

    private static final int TAMANHO_CODIGO = 7;
    private static final int TAMANHO_CODIGO_BARRAS = 13;

    public static String gerarCodigoSeteDigitos() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < TAMANHO_CODIGO; i++) {
            codigo.append(random.nextInt(10));
        }
        return codigo.toString();
    }

    public static String gerarCodigoBarras() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < TAMANHO_CODIGO_BARRAS; i++) {
            codigo.append(random.nextInt(10));
        }
        return codigo.toString();
    }
}
