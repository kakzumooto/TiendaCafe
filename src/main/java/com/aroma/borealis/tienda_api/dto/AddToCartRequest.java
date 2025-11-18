package com.aroma.borealis.tienda_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {
    private Long productoId;
    private Integer cantidad;
}