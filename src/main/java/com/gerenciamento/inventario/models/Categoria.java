package com.gerenciamento.inventario.models;

import com.gerenciamento.inventario.dtos.CategoriaDTOs.DadosCadastroCategoriaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Categoria")
@Table(name = "categoria")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    private String name;

    public Categoria(DadosCadastroCategoriaDTO dto) {
        this.name = dto.name();
    }
}
