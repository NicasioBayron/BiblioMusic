package com.envio.envio.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "envio")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_envio")
    private Long id_envio;

    @Column(name = "id_pago", nullable = false)
    private Long id_pago;

    @Column(name = "id_usuario", nullable = false)
    private Long id_usuario;

    @Column(name = "id_carrito", nullable = false)
    private Long id_carrito;

    @Column(name = "estado_envio", nullable = false)
    private String estado_envio;

    @Column(name = "fecha_envio", nullable = false)
    private LocalDate fecha_envio;

    @Column(name = "direccion_envio", nullable = false)
    private String direccion_envio;
}
