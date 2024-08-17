package com.gerenciamento.inventario.respositories;

import com.gerenciamento.inventario.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String> {
    boolean existsByNameAndIdNot(String id, String name);
}
