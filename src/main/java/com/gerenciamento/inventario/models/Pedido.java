package com.gerenciamento.inventario.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Pedido")
@Table(name = "pedido")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    private int qtd;

    @ManyToOne
    @JoinColumn(name = "cliente_nuit", nullable = false)
    private Cliente clientNuit;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private PrecoProduto productId;

    private double valorTotal;
}
