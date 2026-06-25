package com.productos.productos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;

    private String nombre_producto;
    private String tipo_producto;
    private double precio;
    private int stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @lombok.ToString.Exclude
    @lombok.EqualsAndHashCode.Exclude
    private List<detalle_producto> detalles = new ArrayList<>();

    public void addDetalle(detalle_producto detalle) {
        detalles.add(detalle);
        detalle.setProducto(this);
    }

    public void removeDetalle(detalle_producto detalle) {
        detalles.remove(detalle);
        detalle.setProducto(null);
    }
}
