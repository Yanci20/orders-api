package com.devsoft.orders_api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaDTO {
    private Long id;
    private int numero;
    private String ubicacion;
}
