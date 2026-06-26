package com.carrito.carrito.dto;

import com.carrito.carrito.model.Carrito;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarritoDTO {
    @JsonIgnore
    private Long idCarrito;
    @NotNull(message = "El id del usuario es requerido")
    private Long idUsuario;
    @NotNull(message = "El id del producto es requerido")
    private Long idProducto;
    @NotNull(message = "La cantidad del producto es requerida")
    private int cantidadProducto;

    public Carrito toModel() {
        Carrito carrito = new Carrito();
        carrito.setIdCarrito(this.idCarrito);
        carrito.setIdUsuario(this.idUsuario);
        carrito.setIdProducto(this.idProducto);
        carrito.setCantidadProducto(this.cantidadProducto);
        return carrito;
    }

    public static CarritoDTO fromModel(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setIdCarrito(carrito.getIdCarrito());
        dto.setIdUsuario(carrito.getIdUsuario());
        dto.setIdProducto(carrito.getIdProducto());
        dto.setCantidadProducto(carrito.getCantidadProducto());
        return dto;
    }
}
