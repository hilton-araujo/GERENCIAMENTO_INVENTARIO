package com.gerenciamento.inventario.respositories;

import com.gerenciamento.inventario.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRespository extends JpaRepository<Cliente, String> {
    boolean existsByNuit(String nuit);

    boolean existsByEmail(String email);

    Optional<Cliente> findByNuit(String nuit);
}
