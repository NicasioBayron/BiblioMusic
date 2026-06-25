package com.carrito.carrito.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCarrito")
    private Long idCarrito;

    @Column(name = "cantidadProducto", nullable = false)
    private int cantidadProducto;

    @Column(name = "IdUsuario", nullable = false)
    private Long idUsuario;

    @Column(name = "IdProducto", nullable = false)
    private Long idProducto;
}
