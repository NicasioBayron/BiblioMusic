package com.pago.pago.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pago.pago.model.Pago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoDTO {
    @JsonIgnore
    private Long idPago;

    @NotNull(message = "El id del carrito es requerido")
    private Long idCarrito;

    private Double total;

    private Double precioProducto;

    private Integer cantidad;

    @NotBlank(message = "El medio de pago es requerido")
    private String medioPago;

    @NotBlank(message = "La confirmación de pago es requerida")
    private String confirmacionPago;

    @NotNull(message = "La fecha de pago es requerida")
    private LocalDate fechaPago;

    public Pago toModel() {
        Pago pago = new Pago();
        pago.setIdPago(this.idPago);
        pago.setIdCarrito(this.idCarrito);
        pago.setTotal(this.total != null ? this.total : 0.0);
        pago.setPrecioProducto(this.precioProducto);
        pago.setCantidad(this.cantidad);
        pago.setMedioPago(this.medioPago);
        pago.setConfirmacionPago(this.confirmacionPago);
        pago.setFechaPago(this.fechaPago);
        return pago;
    }

    public static PagoDTO fromModel(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(pago.getIdPago());
        dto.setIdCarrito(pago.getIdCarrito());
        dto.setTotal(pago.getTotal());
        dto.setPrecioProducto(pago.getPrecioProducto());
        dto.setCantidad(pago.getCantidad());
        dto.setMedioPago(pago.getMedioPago());
        dto.setConfirmacionPago(pago.getConfirmacionPago());
        dto.setFechaPago(pago.getFechaPago());
        return dto;
    }
}
