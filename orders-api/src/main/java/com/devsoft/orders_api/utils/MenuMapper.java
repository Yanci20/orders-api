package com.devsoft.orders_api.utils;

import com.devsoft.orders_api.dto.CategoriaDTO;
import com.devsoft.orders_api.dto.MenuDTO;
import com.devsoft.orders_api.entities.Categoria;
import com.devsoft.orders_api.entities.Menu;

public class MenuMapper {
    //metodo para convertir una entidad menu a MenuDTO
   public static MenuDTO toDTO(Menu menu){
       MenuDTO menuDTO = new MenuDTO();
       menuDTO.setId(menu.getId());
       menuDTO.setNombre(menu.getNombre());
       menuDTO.setDescripcion(menu.getDescripcion());
       menuDTO.setTipo(menu.getTipo());
       menuDTO.setPrecioUnitario(menu.getPrecioUnitario());
       menuDTO.setUrlImagen(menu.getUrlImagen());
       menuDTO.setDisponible(menu.isIsDisponible());
       menuDTO.setCategoriaDTO(new CategoriaDTO(menu.getCategoria().getId(),
               menu.getCategoria().getNombre()));
       return menuDTO;
   }
   //Metodo para convertir un MenuDTO  a entity menu
    public static Menu toEntity(MenuDTO dto){
       Menu menu = new Menu();
       menu.setId(dto.getId());
       menu.setNombre(dto.getNombre());
       menu.setDescripcion(dto.getDescripcion());
       menu.setTipo(dto.getTipo());
       menu.setPrecioUnitario(dto.getPrecioUnitario());
       menu.setUrlImagen(dto.getUrlImagen());
       menu.setIsDisponible(dto.isDisponible());
       menu.setCategoria(new Categoria(dto.getCategoriaDTO().getId(),
               dto.getCategoriaDTO().getNombre()));
       return menu;
    }
}
