package com.pago.pago.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name = "pago")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPago", nullable = false)
    private Long idPago;

    @Column(name = "idCarrito", nullable = false)
    private Long idCarrito;

    @Column(name = "total", nullable = false)
    private double total;
    // Solo viven en Java, no se guardan en la base de datos, son para el calculo
    // local del total
    @Transient
    @JsonIgnore
    private Double precioProducto;

    @Transient
    @JsonIgnore
    private Integer cantidad;

    @Column(name = "medioPago", nullable = false)
    private String medioPago;

    @Column(name = "confirmacionPago", nullable = false)
    private String confirmacionPago;

    @Column(name = "fechaPago", nullable = false)
    private LocalDate fechaPago;
}
