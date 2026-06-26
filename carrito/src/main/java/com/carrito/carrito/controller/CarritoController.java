package com.carrito.carrito.controller;

import com.carrito.carrito.dto.CarritoDTO;
import com.carrito.carrito.service.CarritoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import com.carrito.carrito.assembler.CarritoAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CarritoAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CarritoDTO>>> getAllCarritos() {
        List<CarritoDTO> carritos = carritoService.getAllCarritos();
        CollectionModel<EntityModel<CarritoDTO>> model = assembler.toCollectionModel(carritos);
        model.add(linkTo(methodOn(CarritoController.class).getAllCarritos()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<?> getCarritoById(@PathVariable Long idCarrito) {
        CarritoDTO carrito = carritoService.getCarritoById(idCarrito);

        if (carrito == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
        }

        return ResponseEntity.ok(assembler.toModel(carrito));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CollectionModel<EntityModel<CarritoDTO>>> getCarritosByUsuario(@PathVariable Long idUsuario) {
        List<CarritoDTO> carritos = carritoService.getCarritosByUsuario(idUsuario);
        CollectionModel<EntityModel<CarritoDTO>> model = assembler.toCollectionModel(carritos);
        model.add(linkTo(methodOn(CarritoController.class).getCarritosByUsuario(idUsuario)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<?> crearCarrito(@RequestBody CarritoDTO carrito) {
        CarritoDTO creado = carritoService.crearCarrito(carrito);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    @PutMapping("/{idCarrito}")
    public ResponseEntity<?> actualizarCarrito(@PathVariable Long idCarrito, @RequestBody CarritoDTO carrito) {
        CarritoDTO actualizado = carritoService.actualizarCarrito(idCarrito, carrito);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{idCarrito}")
    public ResponseEntity<?> eliminarCarrito(@PathVariable Long idCarrito) {
        boolean eliminado = carritoService.eliminarCarrito(idCarrito);

        if (eliminado) {
            return ResponseEntity.ok("Carrito eliminado exitosamente");
        }

        return ResponseEntity.notFound().build();
    }
}
