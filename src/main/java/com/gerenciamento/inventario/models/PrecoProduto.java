package com.gerenciamento.inventario.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Preco")
@Table(name = "preco")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PrecoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String preco;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
}
