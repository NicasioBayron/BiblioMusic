package com.carrito.carrito.controller;

import com.carrito.carrito.model.Carrito;
import com.carrito.carrito.service.CarritoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public ResponseEntity<List<Carrito>> getAllCarritos() {
        return ResponseEntity.ok(carritoService.getAllCarritos());
    }

    @GetMapping("/{idCarrito}")
    public ResponseEntity<?> getCarritoById(@PathVariable Long idCarrito) {
        Carrito carrito = carritoService.getCarritoById(idCarrito);

        if (carrito == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
        }

        return ResponseEntity.ok(carrito);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Carrito>> getCarritosByUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(carritoService.getCarritosByUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<?> crearCarrito(@RequestBody Carrito carrito) {
        carritoService.crearCarrito(carrito);
        return ResponseEntity.ok("Carrito creado exitosamente");
    }

    @PutMapping("/{idCarrito}")
    public ResponseEntity<?> actualizarCarrito(@PathVariable Long idCarrito, @RequestBody Carrito carrito) {
        Carrito actualizado = carritoService.actualizarCarrito(idCarrito, carrito);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
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
