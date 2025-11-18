package com.aroma.borealis.tienda_api.model;

public enum EstadoOrden {
    PENDIENTE, // Recién creada, esperando pago
    PAGADA,
    ENVIADA,
    ENTREGADA,
    CANCELADA
}