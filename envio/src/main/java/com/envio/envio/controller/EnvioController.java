package com.envio.envio.controller;

import com.envio.envio.DTO.EnvioDTO;
import com.envio.envio.service.EnvioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import com.envio.envio.assembler.EnvioAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/envio")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EnvioDTO>>> getAllEnvios() {
        List<EnvioDTO> envios = envioService.getAllEnvios();
        CollectionModel<EntityModel<EnvioDTO>> model = assembler.toCollectionModel(envios);
        model.add(linkTo(methodOn(EnvioController.class).getAllEnvios()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id_envio}")
    public ResponseEntity<?> getEnvioById(@PathVariable Long id_envio) {
        EnvioDTO envio = envioService.getEnvioById(id_envio);

        if (envio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Envio no encontrado");
        }

        return ResponseEntity.ok(assembler.toModel(envio));
    }

    @PostMapping
    public ResponseEntity<?> crearEnvio(@RequestBody EnvioDTO envio) {
        EnvioDTO creado = envioService.crearEnvio(envio);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    @PutMapping("/{id_envio}")
    public ResponseEntity<?> actualizarEnvio(@PathVariable Long id_envio, @RequestBody EnvioDTO envio) {
        EnvioDTO actualizado = envioService.actualizarEnvio(id_envio, envio);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id_envio}")
    public ResponseEntity<?> eliminarEnvio(@PathVariable Long id_envio) {
        boolean eliminado = envioService.eliminarEnvio(id_envio);

        if (eliminado) {
            return ResponseEntity.ok("Envío eliminado exitosamente");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{correo}")
    public ResponseEntity<CollectionModel<EntityModel<EnvioDTO>>> getEnviosByCorreo(@PathVariable String correo) {
        List<EnvioDTO> envios = envioService.getEnviosByCorreo(correo);
        CollectionModel<EntityModel<EnvioDTO>> model = assembler.toCollectionModel(envios);
        model.add(linkTo(methodOn(EnvioController.class).getEnviosByCorreo(correo)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/fecha/{mes}")
    public ResponseEntity<CollectionModel<EntityModel<EnvioDTO>>> getEnviosByMes(@PathVariable int mes) {
        List<EnvioDTO> envios = envioService.getEnviosByMes(mes);
        CollectionModel<EntityModel<EnvioDTO>> model = assembler.toCollectionModel(envios);
        model.add(linkTo(methodOn(EnvioController.class).getEnviosByMes(mes)).withSelfRel());
        return ResponseEntity.ok(model);
    }
}
