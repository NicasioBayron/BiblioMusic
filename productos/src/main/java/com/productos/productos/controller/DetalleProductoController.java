package com.productos.productos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.productos.productos.model.detalle_producto;
import com.productos.productos.service.DetalleProductoService;

@RestController
@RequestMapping("/producto/detalle")
public class DetalleProductoController {

    @Autowired
    private DetalleProductoService detalleProductoService;

    @GetMapping
    public ResponseEntity<?> getAllDetalles() {
        return ResponseEntity.ok(detalleProductoService.getAllDetalles());
    }

    @PostMapping
    public ResponseEntity<?> crearDetalle(@RequestBody detalle_producto detalle) {
        detalleProductoService.crearDetalle(detalle);
        return ResponseEntity.ok("Detalle creado exitosamente");
    }

    @PutMapping
    public ResponseEntity<?> actualizarDetalle(@RequestBody detalle_producto detalle) {
        detalleProductoService.actualizarDetalle(detalle);
        return ResponseEntity.ok("Detalle actualizado exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetalle(@PathVariable Long id) {
        detalle_producto detalle = detalleProductoService.getDetalleById(id);
        if (detalle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Detalle no encontrado");
        }
        return ResponseEntity.ok(detalle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDetalle(@PathVariable Long id) {
        if (detalleProductoService.eliminarDetalle(id)) {
            return ResponseEntity.ok("Detalle eliminado exitosamente");
        }
        return ResponseEntity.notFound().build();
    }
}
