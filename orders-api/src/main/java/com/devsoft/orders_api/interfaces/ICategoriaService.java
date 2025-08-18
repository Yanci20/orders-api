package com.devsoft.orders_api.interfaces;

import com.devsoft.orders_api.dto.CategoriaDTO;
import com.devsoft.orders_api.entities.Categoria;

import java.util.List;

public interface ICategoriaService {
    List<CategoriaDTO> findAll();
    CategoriaDTO findById(Long id);
    CategoriaDTO save(CategoriaDTO categoriaDTO);
    CategoriaDTO findByNombre(String nombre);
    void delete(Long id);
}
