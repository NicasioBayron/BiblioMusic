package com.resenas.resenas.DTO;

import java.time.LocalDateTime;
import com.resenas.resenas.model.Resenas;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResenasDTO {

    @JsonIgnore
    private Long idResena;

    @NotNull(message = "El ID del producto es requerido")
    private Long idProducto;

    @NotNull(message = "El ID del usuario es requerido")
    private Long idUsuario;

    @NotNull(message = "La calificación es requerida")
    private Integer calificacion;

    @NotBlank(message = "El comentario no puede estar vacío")
    private String comentario;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime fechaCreacion;

    public Resenas toModel() {
        Resenas resena = new Resenas();
        resena.setIdResena(this.idResena);
        resena.setIdProducto(this.idProducto);
        resena.setIdUsuario(this.idUsuario);
        resena.setCalificacion(this.calificacion);
        resena.setComentario(this.comentario);
        resena.setFechaCreacion(this.fechaCreacion);
        return resena;
    }

    public static ResenasDTO fromModel(Resenas resena) {
        ResenasDTO resenasDTO = new ResenasDTO();
        resenasDTO.setIdResena(resena.getIdResena());
        resenasDTO.setIdProducto(resena.getIdProducto());
        resenasDTO.setIdUsuario(resena.getIdUsuario());
        resenasDTO.setCalificacion(resena.getCalificacion());
        resenasDTO.setComentario(resena.getComentario());
        resenasDTO.setFechaCreacion(resena.getFechaCreacion());
        return resenasDTO;
    }
}