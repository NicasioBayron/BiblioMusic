package com.login.login.assembler;

import com.login.login.controller.UsuarioController;
import com.login.login.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).getUsuarioById(usuario.getId(), null)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).getUsuarios(null)).withRel("usuarios"),
                linkTo(methodOn(UsuarioController.class).deleteUsuario(usuario.getId(), null)).withRel("delete"));
    }
}
