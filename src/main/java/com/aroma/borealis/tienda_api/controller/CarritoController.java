package com.aroma.borealis.tienda_api.controller;

import com.aroma.borealis.tienda_api.dto.AddToCartRequest;
import com.aroma.borealis.tienda_api.model.Carrito;
import com.aroma.borealis.tienda_api.service.CarritoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@SecurityRequirement(name = "bearerAuth")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;


    @PostMapping("/add")
    public ResponseEntity<Carrito> agregarProducto(@RequestBody AddToCartRequest request) {
        Carrito carritoActualizado = carritoService.agregarProducto(request);
        return ResponseEntity.ok(carritoActualizado);
    }


    @GetMapping
    public ResponseEntity<Carrito> verCarrito() {
        Carrito carrito = carritoService.getCarrito();
        return ResponseEntity.ok(carrito);
    }
}