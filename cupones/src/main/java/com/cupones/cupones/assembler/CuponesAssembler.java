package com.cupones.cupones.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.cupones.cupones.controller.CuponController;
import com.cupones.cupones.model.Cupones;


@Component
public class CuponesAssembler implements RepresentationModelAssembler<Cupones, EntityModel<Cupones>> {
    @Override
    public EntityModel<Cupones> toModel(Cupones cupon) {
        return EntityModel.of(cupon,
                linkTo(methodOn(CuponController.class).buscarPorId(cupon.getIdCupon())).withSelfRel(),
                linkTo(methodOn(CuponController.class).actualizarCupon(cupon.getIdCupon(), null))
                        .withRel("Actualizar cupon").withType("PUT"),
                linkTo(methodOn(CuponController.class).eliminarCupon(cupon.getIdCupon()))
                        .withRel("Eliminar cupon").withType("DELETE"));
    }
}
