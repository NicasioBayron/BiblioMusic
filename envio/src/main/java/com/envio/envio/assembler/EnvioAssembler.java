package com.envio.envio.assembler;

import com.envio.envio.DTO.EnvioDTO;
import com.envio.envio.controller.EnvioController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;

@Component
public class EnvioAssembler implements RepresentationModelAssembler<EnvioDTO, EntityModel<EnvioDTO>> {

    @Override
    public EntityModel<EnvioDTO> toModel(EnvioDTO envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioController.class).getEnvioById(envio.getId_envio())).withSelfRel(),
                linkTo(methodOn(EnvioController.class).getAllEnvios()).withRel("envios"),
                linkTo(methodOn(EnvioController.class).eliminarEnvio(envio.getId_envio())).withRel("delete"));
    }
}
