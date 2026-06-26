package com.resenas.resenas.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.resenas.resenas.controller.ResenasController;
import com.resenas.resenas.model.Resenas;

@Component
public class ResenasAssembler implements RepresentationModelAssembler<Resenas, EntityModel<Resenas>> {
        @Override
        public EntityModel<Resenas> toModel(Resenas resena) {
                return EntityModel.of(resena,
                                linkTo(methodOn(ResenasController.class).buscarPorId(resena.getIdResena()))
                                                .withSelfRel(),
                                linkTo(methodOn(ResenasController.class).actualizarResena(resena.getIdResena(), null))
                                                .withRel("Actualizar resena").withType("PUT"),
                                linkTo(methodOn(ResenasController.class).eliminarResena(resena.getIdResena()))
                                                .withRel("Eliminar resena").withType("DELETE"));
        }
}
