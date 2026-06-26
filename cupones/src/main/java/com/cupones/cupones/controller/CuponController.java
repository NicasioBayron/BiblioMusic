package com.cupones.cupones.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cupones.cupones.assembler.CuponesAssembler;
import com.cupones.cupones.model.Cupones;
import com.cupones.cupones.service.CuponServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cupones")
public class CuponController {

    private final CuponServices cuponServices;
    private final CuponesAssembler cuponesAssembler;

    public CuponController(CuponServices cuponServices, CuponesAssembler cuponesAssembler) {
        this.cuponServices = cuponServices;
        this.cuponesAssembler = cuponesAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Cupones>> listarCupones() {
        log.info("Obteniendo todos los cupones");
        List<EntityModel<Cupones>> cupones = cuponServices.listarCupones().stream()
                .map(t -> cuponesAssembler.toModel(t))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Cupones>> collectionModel = CollectionModel.of(cupones,
                linkTo(methodOn(CuponController.class).listarCupones()).withSelfRel());
        collectionModel
                .add(linkTo(methodOn(CuponController.class).crearCupon(null)).withRel("crear cupon").withType("POST"));

        return collectionModel;
    }

    @GetMapping("/{idCupon}")
    public EntityModel<Cupones> buscarPorId(@PathVariable Long idCupon) {
        log.info("Obteniendo cupon con id: " + idCupon);
        Cupones cupon = cuponServices.buscarPorId(idCupon);
        EntityModel<Cupones> modelo = cuponesAssembler.toModel(cupon);
        modelo.add(
                linkTo(methodOn(CuponController.class).listarCupones()).withRel("Todos los cupones").withType("GET"));
        return modelo;
    }

    @PostMapping
    public ResponseEntity<EntityModel<Cupones>> crearCupon(@RequestBody Cupones cupon) {
        log.info("Creando cupon");
        return ResponseEntity.ok(cuponesAssembler.toModel(cuponServices.crearCupon(cupon)));
    }

    @PutMapping("/{idCupon}")
    public ResponseEntity<EntityModel<Cupones>> actualizarCupon(@PathVariable Long idCupon,
            @RequestBody Cupones cupon) {
        log.info("Actualizando cupon con id: " + idCupon);
        return ResponseEntity.ok(cuponesAssembler.toModel(cuponServices.actualizarCupon(idCupon, cupon)));
    }

    @DeleteMapping("/{idCupon}")
    public ResponseEntity<Void> eliminarCupon(@PathVariable Long idCupon) {
        log.info("Eliminando cupon con id: " + idCupon);
        cuponServices.eliminarCupon(idCupon);
        return ResponseEntity.noContent().build();
    }
}