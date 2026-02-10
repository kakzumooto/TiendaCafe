package com.aroma.borealis.tienda_api.controller;

import com.aroma.borealis.tienda_api.model.Producto;
import com.aroma.borealis.tienda_api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:5173") // <--- ¡IMPORTANTE PARA REACT!
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // 1. OBTENER TODOS
    @GetMapping
    public List<Producto> obtenerTodosLosProductos() {
        return productoService.getAllProductos();
    }

    // 2. OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id){
        Producto producto = productoService.getProductoById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    // 3. CREAR
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        // Usamos 'guardarProducto' que es el que agregamos al servicio
        Producto productoGuardado = productoService.guardarProducto(producto);
        return new ResponseEntity<>(productoGuardado, HttpStatus.CREATED);
    }

    // 4. ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable long id, @RequestBody Producto productoDetails){
        Producto productoActualizado = productoService.updateProducto(id, productoDetails);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }

    // 5. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productoService.deleteProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}