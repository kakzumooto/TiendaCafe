package com.aroma.borealis.tienda_api.controller;

import com.aroma.borealis.tienda_api.model.Orden;
import com.aroma.borealis.tienda_api.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    @Autowired
    private OrderService orderService;



    @PostMapping("/checkout")
    public ResponseEntity<Orden> checkout() {
        Orden nuevaOrden = orderService.placeOrder();
        return ResponseEntity.ok(nuevaOrden);
    }


    @GetMapping("/mis-ordenes")
    public ResponseEntity<List<Orden>> misOrdenes(Authentication authentication) {

        String email = authentication.getName();

        List<Orden> ordenes = orderService.obtenerMisOrdenes(email);

        return ResponseEntity.ok(ordenes);
    }
}