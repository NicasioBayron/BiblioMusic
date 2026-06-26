package com.carrito.carrito.assembler;

import com.carrito.carrito.controller.CarritoController;
import com.carrito.carrito.dto.CarritoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CarritoAssembler implements RepresentationModelAssembler<CarritoDTO, EntityModel<CarritoDTO>> {

    @Override
    public EntityModel<CarritoDTO> toModel(CarritoDTO carrito) {
        return EntityModel.of(carrito,
                linkTo(methodOn(CarritoController.class).getCarritoById(carrito.getIdCarrito())).withSelfRel(),
                linkTo(methodOn(CarritoController.class).getAllCarritos()).withRel("carritos"),
                linkTo(methodOn(CarritoController.class).eliminarCarrito(carrito.getIdCarrito())).withRel("delete"),
                linkTo(methodOn(CarritoController.class).getCarritosByUsuario(carrito.getIdUsuario()))
                        .withRel("carritos-usuario"));
    }
}
