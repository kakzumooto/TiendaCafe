package com.aroma.borealis.tienda_api.service;

import com.aroma.borealis.tienda_api.model.Producto;
import com.aroma.borealis.tienda_api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aroma.borealis.tienda_api.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto createProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto getProductoById (Long id){
        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Producto no encontrado con id" + id)
                        );
    }

    public Producto updateProducto(Long id, Producto productoDetails){
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Producto no encontrado con id"+ id)
                );
        productoExistente.setNombre(productoDetails.getNombre());
        productoExistente.setDescripcion(productoDetails.getDescripcion());
        productoExistente.setPrecio(productoDetails.getPrecio());
        productoExistente.setStock(productoDetails.getStock());
        productoExistente.setImageUrl(productoDetails.getImageUrl());
        productoExistente.setCategoria(productoDetails.getCategoria());

        return productoRepository.save(productoExistente);
    }

    public void deleteProducto(long id){
        if (!productoRepository.existsById(id)){
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }



}