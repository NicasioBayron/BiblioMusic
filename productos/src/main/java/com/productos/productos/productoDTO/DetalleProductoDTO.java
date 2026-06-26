package com.productos.productos.productoDTO;

import com.productos.productos.model.detalle_producto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleProductoDTO {
    private Long id_detalle;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    @NotBlank(message = "El genero es obligatorio")
    private String genero;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    public detalle_producto toModel() {
        detalle_producto detalle = new detalle_producto();
        detalle.setId_detalle(id_detalle);
        detalle.setAutor(autor);
        detalle.setGenero(genero);
        detalle.setDescripcion(descripcion);
        return detalle;
    }

    public static DetalleProductoDTO fromModel(detalle_producto detalle) {
        if (detalle == null)
            return null;

        return DetalleProductoDTO.builder()
            .id_detalle(detalle.getId_detalle())
            .autor(detalle.getAutor())
            .genero(detalle.getGenero())
            .descripcion(detalle.getDescripcion())
            .build();
    }
}
