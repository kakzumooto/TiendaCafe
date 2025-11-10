package com.aroma.borealis.tienda_api.controller;

import com.aroma.borealis.tienda_api.model.Producto;
import com.aroma.borealis.tienda_api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id){
        Producto producto = productoService.getProductoById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto productoGuardado = productoService.createProducto(producto);
        return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable long id, @RequestBody Producto productoDetails){
        Producto productoActualizado = productoService.updateProducto(id, productoDetails);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productoService.deleteProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}