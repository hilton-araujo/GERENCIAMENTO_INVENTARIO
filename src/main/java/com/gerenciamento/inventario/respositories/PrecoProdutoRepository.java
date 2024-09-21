package com.gerenciamento.inventario.respositories;

import com.gerenciamento.inventario.models.PrecoProduto;
import com.gerenciamento.inventario.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrecoProdutoRepository extends JpaRepository<PrecoProduto, String> {
    boolean existsByPreco(String preco);

    boolean existsByProduto(Produto produto);

    Optional<PrecoProduto> findByProdutoId(String id);
}
