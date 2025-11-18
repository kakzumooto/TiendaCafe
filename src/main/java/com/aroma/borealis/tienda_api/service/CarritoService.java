package com.aroma.borealis.tienda_api.service;

import com.aroma.borealis.tienda_api.dto.AddToCartRequest;
import com.aroma.borealis.tienda_api.model.Carrito;
import com.aroma.borealis.tienda_api.model.ItemCarrito;
import com.aroma.borealis.tienda_api.model.Producto;
import com.aroma.borealis.tienda_api.model.Usuario;
import com.aroma.borealis.tienda_api.repository.CarritoRepository;
import com.aroma.borealis.tienda_api.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public Carrito agregarProducto(AddToCartRequest request) {

        Usuario usuario = getAuthenticatedUser();

        Carrito carrito = getOrCreateCarrito(usuario);

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + request.getProductoId()));

        Optional<ItemCarrito> itemExistente = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(request.getProductoId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + request.getCantidad());
            item.setSubtotal(item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad())));
        } else {
            ItemCarrito nuevoItem = ItemCarrito.builder()
                    .producto(producto)
                    .cantidad(request.getCantidad())
                    .precioUnitario(producto.getPrecio()) // Usamos el precio actual del producto
                    .carrito(carrito) // Asignamos el carrito actual
                    .build();

            nuevoItem.setSubtotal(nuevoItem.getPrecioUnitario().multiply(new BigDecimal(nuevoItem.getCantidad())));
            carrito.getItems().add(nuevoItem);
        }

        recalculateTotal(carrito);

        return carritoRepository.save(carrito);
    }

    private Usuario getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("Usuario no autenticado");
        }
        return (Usuario) authentication.getPrincipal();
    }

    private Carrito getOrCreateCarrito(Usuario usuario) {
        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseGet(() -> Carrito.builder().usuario(usuario)
                        .items(new ArrayList<>())
                        .total(BigDecimal.ZERO)
                        .build());
    }

    public void recalculateTotal(Carrito carrito) {
        BigDecimal total = carrito.getItems().stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        carrito.setTotal(total);
    }

    public Carrito getCarrito() {
        Usuario usuario = getAuthenticatedUser();
        return carritoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RuntimeException("No tiene un carrito activo"));
    }

    @Transactional
    public void vaciarCarrito() {
        Carrito carrito = getCarrito();
        carrito.getItems().clear();
        carrito.setTotal(BigDecimal.ZERO);
        carritoRepository.save(carrito);
    }
}