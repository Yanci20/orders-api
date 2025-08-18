package com.devsoft.orders_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleOrdenDTO {
    private Long id;
    private MenuDTO menuDTO;
    private Integer cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
}
