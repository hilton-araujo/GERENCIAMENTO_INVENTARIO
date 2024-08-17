package com.gerenciamento.inventario.services;

import com.gerenciamento.inventario.dtos.CategoriaDTOs.DadosAtualizarCategoria;
import com.gerenciamento.inventario.dtos.CategoriaDTOs.DadosCadastroCategoriaDTO;
import com.gerenciamento.inventario.dtos.CategoriaDTOs.DadosVisualizacaoCategoriaDTO;
import com.gerenciamento.inventario.models.Categoria;
import com.gerenciamento.inventario.respositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria cadastrar(Categoria categoria){
        return repository.save(categoria);
    }

    public void registrar(DadosCadastroCategoriaDTO dto){
        try {

            Categoria categoria;
            categoria = new Categoria(dto);
            cadastrar(categoria);

        }catch (Exception exception){

        }
    }

    public List<DadosVisualizacaoCategoriaDTO> listar(){
        List<Categoria> categorias = repository.findAll();

        List<DadosVisualizacaoCategoriaDTO> dtos = new ArrayList<>();

        for (Categoria categoria : categorias){
            DadosVisualizacaoCategoriaDTO visualizacaoCategoriaDTO = new DadosVisualizacaoCategoriaDTO(
                    categoria.getId(),
                    categoria.getName()
            );
            dtos.add(visualizacaoCategoriaDTO);
        }

        return dtos;
    }

    public void atualizarCategoria(DadosAtualizarCategoria dto){
        try {

            Categoria categoria = repository.findById(dto.id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

            if (!categoria.getName().equals(dto.name())){
                boolean existeNome = repository.existsByNameAndIdNot(dto.name(), dto.id());
                if (existeNome){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Categoria com nome " +dto.name() +" já existe");
                }
            }

            categoria.setName(dto.name());
            this.cadastrar(categoria);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar categoria", e);
        }
    }

}
