package com.devsoft.orders_api.dto;

import com.devsoft.orders_api.utils.EstadoOrden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenDTO {
    private Long id;
    private String correlativo;
    private LocalDate fecha;
    private LocalTime hora;
    private EstadoOrden estado;
    private BigDecimal total;
    private ClienteDTO clienteDTO;
    private MesaDTO mesaDTO;
    private UsuarioDTO usuarioDTO;
    //coleccion para el detalle de la orden
    private List<DetalleOrdenDTO> detalle;
}
