package com.devsoft.orders_api.repository;

import com.devsoft.orders_api.entities.Orden;
import com.devsoft.orders_api.utils.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    //metodo para obtener listado de orden por estado
    List<Orden> findByEstado(EstadoOrden estado);

    //metodo para obtener el correlativo maximo por mes y ano
    @Query("SELECT MAX(CAST(SUBSTRING(o.correlativo,7) AS Long)) FROM Orden o WHERE SUBSTRING(o.correlativo,1,6) = :yearMonth")
    Long finMaxCorrelativoByYearMonth(@Param("yearMonth") String yearMonth);
}