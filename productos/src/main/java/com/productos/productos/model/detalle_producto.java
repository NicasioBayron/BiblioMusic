package com.productos.productos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class detalle_producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_detalle;

    private String autor;
    private String genero;
    
    @Column(length = 1000)
    private String descripcion;

    // Relación bidireccional con Producto
    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @JsonIgnore
    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    private Producto producto;
}
