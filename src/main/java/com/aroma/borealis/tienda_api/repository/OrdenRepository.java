package com.aroma.borealis.tienda_api.repository;

import com.aroma.borealis.tienda_api.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

}