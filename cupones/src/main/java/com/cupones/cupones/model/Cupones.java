package com.cupones.cupones.model;

import java.time.LocalDate;

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
@Table(name = "cupones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cupones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCupon")
    private Long idCupon;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "porcentajeDescuento", nullable = false)
    private Integer porcentajeDescuento;

    @Column(name = "fechaExpiracion", nullable = false)
    private LocalDate fechaExpiracion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}