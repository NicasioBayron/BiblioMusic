package com.pago.pago.controller;

import com.pago.pago.model.Pago;
import com.pago.pago.service.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> getAllPagos() {
        return ResponseEntity.ok(pagoService.getAllPagos());
    }

    @GetMapping("/{idPago}")
    public ResponseEntity<?> getPagoById(@PathVariable Long idPago) {
        Pago pago = pagoService.getPagoById(idPago);

        if (pago == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pago no encontrado");
        }

        return ResponseEntity.ok(pago);
    }

    @PostMapping
    public ResponseEntity<?> crearPago(@RequestBody Pago pago) {
        pagoService.crearPago(pago);
        return ResponseEntity.ok("Compra realizada exitosamente");
    }

    @PutMapping("/{idPago}")
    public ResponseEntity<?> actualizarPago(@PathVariable Long idPago, @RequestBody Pago pago) {
        Pago actualizado = pagoService.actualizarPago(idPago, pago);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
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
    public ResponseEntity<List<Pago>> getPagosByCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(pagoService.getPagosByCorreo(correo));
    }

    @GetMapping("/fecha/{mes}")
    public ResponseEntity<List<Pago>> getPagosByMes(@PathVariable int mes) {
        return ResponseEntity.ok(pagoService.getPagosByMes(mes));
    }
}
