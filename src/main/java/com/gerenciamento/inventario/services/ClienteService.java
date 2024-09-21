package com.gerenciamento.inventario.services;

import com.gerenciamento.inventario.dtos.ClienteDTOs.DadosCadastroCliente;
import com.gerenciamento.inventario.dtos.ClienteDTOs.DadosListagemCliente;
import com.gerenciamento.inventario.models.Cliente;
import com.gerenciamento.inventario.respositories.ClienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRespository respository;

    public Cliente create(Cliente cliente){
        return respository.save(cliente);
    }

    public void registrarCliente(DadosCadastroCliente dadosCadastroCliente){
        try {
            Cliente cliente;

            boolean clienteExistePorNuit = respository.existsByNuit(dadosCadastroCliente.nuit());
            boolean clienteExistePorEmail = respository.existsByEmail(dadosCadastroCliente.email());

            if (clienteExistePorNuit || clienteExistePorEmail){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já existe");
            }

            cliente = new Cliente();
            cliente.setNome(dadosCadastroCliente.nome());
            cliente.setEmail(dadosCadastroCliente.email());
            cliente.setNuit(dadosCadastroCliente.nuit());

            create(cliente);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar cliente", e);
        }
    }

    public List<DadosListagemCliente> listar(){
        List<Cliente> clientes = respository.findAll();

        List<DadosListagemCliente> listagemClientes = new ArrayList<>();

        for (Cliente cliente : clientes) {
            DadosListagemCliente dadosListagemCliente = new DadosListagemCliente(
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getEmail(),
                    cliente.getNuit()
            );
            listagemClientes.add(dadosListagemCliente);
        }
        return listagemClientes;
    }
}
