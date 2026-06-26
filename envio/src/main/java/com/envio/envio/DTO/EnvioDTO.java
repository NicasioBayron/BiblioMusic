package com.envio.envio.DTO;

import com.envio.envio.model.Envio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EnvioDTO {
    @JsonIgnore
    private Long id_envio;
    @NotNull(message = "El id_pago es requerido")
    private Long id_pago;
    @NotNull(message = "El id_usuario es requerido")
    private Long id_usuario;
    @NotNull(message = "El id_carrito es requerido")
    private Long id_carrito;
    @NotBlank(message = "El estado_envio es requerido")
    private String estado_envio;
    @NotNull(message = "La fecha_envio es requerida")
    private LocalDate fecha_envio;
    @NotBlank(message = "La direccion_envio es requerida")
    private String direccion_envio;

    public Envio toModel() {
        Envio envio = new Envio();
        envio.setId_envio(this.id_envio);
        envio.setId_pago(this.id_pago);
        envio.setId_usuario(this.id_usuario);
        envio.setId_carrito(this.id_carrito);
        envio.setEstado_envio(this.estado_envio);
        envio.setFecha_envio(this.fecha_envio);
        envio.setDireccion_envio(this.direccion_envio);
        return envio;
    }

    public static EnvioDTO fromModel(Envio envio) {
        EnvioDTO envioDTO = new EnvioDTO();
        envioDTO.setId_envio(envio.getId_envio());
        envioDTO.setId_pago(envio.getId_pago());
        envioDTO.setId_usuario(envio.getId_usuario());
        envioDTO.setId_carrito(envio.getId_carrito());
        envioDTO.setEstado_envio(envio.getEstado_envio());
        envioDTO.setFecha_envio(envio.getFecha_envio());
        envioDTO.setDireccion_envio(envio.getDireccion_envio());
        return envioDTO;
    }
}
