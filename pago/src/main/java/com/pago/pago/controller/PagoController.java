package com.pago.pago.controller;

import com.pago.pago.DTO.PagoDTO;
import com.pago.pago.service.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import com.pago.pago.assembler.PagoAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> getAllPagos() {
        List<PagoDTO> pagos = pagoService.getAllPagos();
        CollectionModel<EntityModel<PagoDTO>> model = assembler.toCollectionModel(pagos);
        model.add(linkTo(methodOn(PagoController.class).getAllPagos()).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{idPago}")
    public ResponseEntity<?> getPagoById(@PathVariable Long idPago) {
        PagoDTO pago = pagoService.getPagoById(idPago);

        if (pago == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado");
        }

        return ResponseEntity.ok(assembler.toModel(pago));
    }

    @PostMapping
    public ResponseEntity<?> crearPago(@RequestBody PagoDTO pago) {
        PagoDTO creado = pagoService.crearPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    @PutMapping("/{idPago}")
    public ResponseEntity<?> actualizarPago(@PathVariable Long idPago, @RequestBody PagoDTO pago) {
        PagoDTO actualizado = pagoService.actualizarPago(idPago, pago);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{idPago}")
    public ResponseEntity<?> eliminarPago(@PathVariable Long idPago) {
        boolean eliminado = pagoService.eliminarPago(idPago);

        if (eliminado) {
            return ResponseEntity.ok("Pago eliminado exitosamente");
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{correo}")
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> getPagosByCorreo(@PathVariable String correo) {
        List<PagoDTO> pagos = pagoService.getPagosByCorreo(correo);
        CollectionModel<EntityModel<PagoDTO>> model = assembler.toCollectionModel(pagos);
        model.add(linkTo(methodOn(PagoController.class).getPagosByCorreo(correo)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @GetMapping("/fecha/{mes}")
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> getPagosByMes(@PathVariable int mes) {
        List<PagoDTO> pagos = pagoService.getPagosByMes(mes);
        CollectionModel<EntityModel<PagoDTO>> model = assembler.toCollectionModel(pagos);
        model.add(linkTo(methodOn(PagoController.class).getPagosByMes(mes)).withSelfRel());
        return ResponseEntity.ok(model);
    }
}
