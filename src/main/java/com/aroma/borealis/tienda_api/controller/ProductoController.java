package com.aroma.borealis.tienda_api.controller;

import com.aroma.borealis.tienda_api.model.Producto;
import com.aroma.borealis.tienda_api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Endpoint para OBTENER TODOS los productos
    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.getAllProductos();
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto productoGuardado = productoService.createProducto(producto);
        return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
    }


}