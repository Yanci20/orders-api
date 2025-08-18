package com.devsoft.orders_api.interfaces;

import com.devsoft.orders_api.dto.MenuDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMenuService {
    List<MenuDTO>findAll();
    MenuDTO findById(Long id);
    MenuDTO findByNombre(String nombre);
    MenuDTO save(MenuDTO dto, MultipartFile imageFile)throws IOException;
    void delete(Long id);

}
