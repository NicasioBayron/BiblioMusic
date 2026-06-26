package com.resenas.resenas.model;

import java.time.LocalDateTime;

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
@Table(name = "resenas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resenas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idResena")
    private Long idResena;

    @Column(name = "idProducto", nullable = false)
    private Long idProducto;

    @Column(name = "idUsuario", nullable = false)
    private Long idUsuario;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "comentario", nullable = false)
    private String comentario;

    @Column(name = "fechaCreacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
