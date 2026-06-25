package com.productos.productos.productoDTO;

import com.productos.productos.model.Producto;
import com.productos.productos.model.detalle_producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Long id_producto;
    private String nombre_producto;
    private String tipo_producto;
    private double precio;
    private int stock;
    private List<detalle_producto> detalles;

    public Producto toModel() {
        Producto p = new Producto();
        p.setId_producto(this.id_producto);
        p.setNombre_producto(this.nombre_producto);
        p.setTipo_producto(this.tipo_producto);
        p.setPrecio(this.precio);
        p.setStock(this.stock);
        
        if (this.detalles != null) {
            for (detalle_producto d : this.detalles) {
                p.addDetalle(d);
            }
        }
        return p;
    }

    public static ProductoDTO fromModel(Producto p) {
        if (p == null)
            return null;
        return new ProductoDTO(
            p.getId_producto(), 
            p.getNombre_producto(), 
            p.getTipo_producto(), 
            p.getPrecio(),
            p.getStock(),
            p.getDetalles()
        );
    }
}