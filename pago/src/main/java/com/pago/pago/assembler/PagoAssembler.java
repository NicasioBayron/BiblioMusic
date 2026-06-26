package com.pago.pago.assembler;

import com.pago.pago.controller.PagoController;
import com.pago.pago.DTO.PagoDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PagoAssembler implements RepresentationModelAssembler<PagoDTO, EntityModel<PagoDTO>> {

    @Override
    public EntityModel<PagoDTO> toModel(PagoDTO pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoController.class).getPagoById(pago.getIdPago())).withSelfRel(),
                linkTo(methodOn(PagoController.class).getAllPagos()).withRel("pagos"),
                linkTo(methodOn(PagoController.class).eliminarPago(pago.getIdPago())).withRel("delete"));
    }
}
