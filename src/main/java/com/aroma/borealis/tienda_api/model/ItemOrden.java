package com.aroma.borealis.tienda_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "items_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreProducto;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    @JsonIgnore
    private Orden orden;
}