package com.devsoft.orders_api.dto;

import com.devsoft.orders_api.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private BigDecimal precioUnitario;
    private String urlImagen;
    private boolean disponible;
    private CategoriaDTO categoriaDTO;

}
