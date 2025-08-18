package com.devsoft.orders_api.interfaces;

import com.devsoft.orders_api.dto.OrdenDTO;
import com.devsoft.orders_api.utils.EstadoOrden;

import javax.print.attribute.standard.OutputDeviceAssigned;
import java.util.List;

public interface IOrdenService {
    List<OrdenDTO> findAll();
    List<OrdenDTO> findByEstado(EstadoOrden estado);
    OrdenDTO findById(Long id);
    OrdenDTO registerOrUpdate(OrdenDTO ordenDTO);
    void anular(Long id);
    OrdenDTO changeState(OrdenDTO dto);

}
