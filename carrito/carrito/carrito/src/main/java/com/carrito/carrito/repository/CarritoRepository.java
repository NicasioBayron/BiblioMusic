package com.carrito.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrito.carrito.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    java.util.List<Carrito> findByIdUsuario(Long idUsuario);

}
