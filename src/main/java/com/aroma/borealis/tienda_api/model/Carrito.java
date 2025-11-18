package com.aroma.borealis.tienda_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal total = BigDecimal.ZERO;


    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items = new ArrayList<>();
}