package com.aroma.borealis.tienda_api.service;

import com.aroma.borealis.tienda_api.exception.ResourceNotFoundException;
import com.aroma.borealis.tienda_api.model.*;
import com.aroma.borealis.tienda_api.repository.OrdenRepository;
import com.aroma.borealis.tienda_api.repository.ProductoRepository;
import com.aroma.borealis.tienda_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;
    @Transactional
    public Orden placeOrder() {

        Carrito carrito = carritoService.getCarrito();

        if (carrito.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito está vacío. Agregue productos antes de pagar.");
        }

        List<ItemOrden> itemsOrden = carrito.getItems().stream()
                .map(itemCarrito -> {

                    Producto producto = itemCarrito.getProducto();
                    if (producto.getStock() < itemCarrito.getCantidad()) {
                        // Si el stock es insuficiente, lanzamos una excepción.
                        throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
                    }

                    producto.setStock(producto.getStock() - itemCarrito.getCantidad());
                    productoRepository.save(producto); // Guardar el stock actualizado

                    return ItemOrden.builder()
                            .nombreProducto(producto.getNombre())
                            .cantidad(itemCarrito.getCantidad())
                            .precioUnitario(itemCarrito.getPrecioUnitario())
                            .subtotal(itemCarrito.getSubtotal())
                            .build();
                })
                .collect(Collectors.toList());


        Orden orden = Orden.builder()
                .usuario(carrito.getUsuario())
                .fechaOrden(LocalDateTime.now())
                .total(carrito.getTotal())
                .items(itemsOrden)
                .estado(EstadoOrden.PENDIENTE)
                .build();

        itemsOrden.forEach(item -> item.setOrden(orden));


        carrito.getItems().clear();
        carrito.setTotal(java.math.BigDecimal.ZERO);
        carritoService.recalculateTotal(carrito);
        carritoService.saveCarrito(carrito);
        return ordenRepository.save(orden);
    }

    public List<Orden> obtenerMisOrdenes(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ordenRepository.findByUsuarioId(usuario.getId());
    }
}