package com.gerenciamento.inventario.respositories;

import com.gerenciamento.inventario.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, String id);

}
