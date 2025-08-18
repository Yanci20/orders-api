package com.devsoft.orders_api.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_ordenes", schema = "public", catalog = "orders")
public class DetalleOrden implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Cantidad", nullable = false)
    private int cantidad;
    @Column(name = "precio", nullable = false, precision = 8, scale = 2)
    private BigDecimal precio;
    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private  BigDecimal subtotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, referencedColumnName = "id")
    private Menu menu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference
    private Orden orden;
}
