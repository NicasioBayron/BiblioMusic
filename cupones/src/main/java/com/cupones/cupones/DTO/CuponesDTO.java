package com.cupones.cupones.DTO;

import java.time.LocalDate;

import com.cupones.cupones.model.Cupones;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuponesDTO {

    @JsonIgnore
    private Long idCupon;

    @NotBlank(message = "El código del cupón no puede estar vacío")
    private String codigo;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 1, message = "El descuento mínimo es 1%")
    @Max(value = 100, message = "El descuento máximo es 100%")
    private Integer porcentajeDescuento;

    @NotNull(message = "La fecha de expiración es obligatoria")
    private LocalDate fechaExpiracion;

    @NotNull(message = "Debe especificar si el cupón está activo o no")
    private Boolean activo;

    public Cupones toModel() {
        return new Cupones(idCupon, codigo, porcentajeDescuento, fechaExpiracion, activo);
    }

    public static CuponesDTO fromModel(Cupones c) {
        if (c == null)
            return null;
        return new CuponesDTO(c.getIdCupon(), c.getCodigo(), c.getPorcentajeDescuento(), c.getFechaExpiracion(),
                c.getActivo());
    }
}