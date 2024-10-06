package com.gerenciamento.inventario.respositories;

import com.gerenciamento.inventario.models.Cliente;
import com.gerenciamento.inventario.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, String> {
    List<Pedido> findByClientNuit(Cliente cliente);
}
