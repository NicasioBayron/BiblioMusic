package com.resenas.resenas.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resenas.resenas.assembler.ResenasAssembler;
import com.resenas.resenas.model.Resenas;
import com.resenas.resenas.service.ResenasService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/resenas")
public class ResenasController {

    private final ResenasService resenasService;
    private final ResenasAssembler resenasAssembler;

    public ResenasController(ResenasService resenasService, ResenasAssembler resenasAssembler) {
        this.resenasService = resenasService;
        this.resenasAssembler = resenasAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Resenas>> listarResenas() {
        log.info("Obteniendo todas las resenas");
        List<EntityModel<Resenas>> resenas = resenasService.listarResenas().stream()
                .map(t -> resenasAssembler.toModel(t))
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Resenas>> collectionModel = CollectionModel.of(resenas,
                linkTo(methodOn(ResenasController.class).listarResenas()).withSelfRel());
        collectionModel.add(
                linkTo(methodOn(ResenasController.class).crearResena(null)).withRel("crear resena").withType("POST"));

        return collectionModel;
    }

    @GetMapping("/{idResena}")
    public EntityModel<Resenas> buscarPorId(@PathVariable Long idResena) {
        log.info("Obteniendo resena con id: " + idResena);
        Resenas resena = resenasService.buscarPorId(idResena);
        EntityModel<Resenas> modelo = resenasAssembler.toModel(resena);
        modelo.add(
                linkTo(methodOn(ResenasController.class).listarResenas()).withRel("Todas las resenas").withType("GET"));
        return modelo;
    }

    @PostMapping
    public ResponseEntity<EntityModel<Resenas>> crearResena(@RequestBody Resenas resena) {
        log.info("Creando resena");
        return ResponseEntity.ok(resenasAssembler.toModel(resenasService.crearResena(resena)));
    }

    @PutMapping("/{idResena}")
    public ResponseEntity<EntityModel<Resenas>> actualizarResena(@PathVariable Long idResena,
            @RequestBody Resenas resena) {
        log.info("Actualizando resena con id: " + idResena);
        return ResponseEntity.ok(resenasAssembler.toModel(resenasService.actualizarResena(idResena, resena)));
    }

    @DeleteMapping("/{idResena}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long idResena) {
        log.info("Eliminando resena con id: " + idResena);
        resenasService.eliminarResena(idResena);
        return ResponseEntity.noContent().build();
    }
}