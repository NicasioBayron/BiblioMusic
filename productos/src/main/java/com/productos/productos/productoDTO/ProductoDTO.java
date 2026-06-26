package com.productos.productos.productoDTO;

import com.productos.productos.model.Producto;
import com.productos.productos.productoDTO.DetalleProductoDTO;

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
    private List<DetalleProductoDTO> detalles;

    public Producto toModel() {
        Producto p = new Producto();
        p.setId_producto(this.id_producto);
        p.setNombre_producto(this.nombre_producto);
        p.setTipo_producto(this.tipo_producto);
        p.setPrecio(this.precio);
        p.setStock(this.stock);

        if (this.detalles != null) {
            for (DetalleProductoDTO d : this.detalles) {
                p.addDetalle(d.toModel());
            }
        }
        return p;
    }

    public static ProductoDTO fromModel(Producto p) {
        if (p == null)
            return null;

        List<DetalleProductoDTO> detalleDto = null;
        if (p.getDetalles() != null) {
            detalleDto = p.getDetalles().stream().map(DetalleProductoDTO::fromModel).toList();
        }
        return new ProductoDTO(
            p.getId_producto(),
            p.getNombre_producto(),
            p.getTipo_producto(),
            p.getPrecio(),
            p.getStock(),
            detalleDto
        );
    }
}