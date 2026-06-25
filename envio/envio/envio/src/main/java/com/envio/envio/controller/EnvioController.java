package com.envio.envio.controller;

import com.envio.envio.model.Envio;
import com.envio.envio.service.EnvioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envio")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<Envio>> getAllEnvios() {
        return ResponseEntity.ok(envioService.getAllEnvios());
    }

    @GetMapping("/{id_envio}")
    public ResponseEntity<?> getEnvioById(@PathVariable Long id_envio) {
        Envio envio = envioService.getEnvioById(id_envio);

        if (envio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Envio no encontrado");
        }

        return ResponseEntity.ok(envio);
    }

    @PostMapping
    public ResponseEntity<?> crearEnvio(@RequestBody Envio envio) {
        envioService.crearEnvio(envio);
        return ResponseEntity.ok("Envio Agregado Exitosamente");
    }

    @PutMapping("/{id_envio}")
    public ResponseEntity<?> actualizarEnvio(@PathVariable Long id_envio, @RequestBody Envio envio) {
        Envio actualizado = envioService.actualizarEnvio(id_envio, envio);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
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
    public ResponseEntity<List<Envio>> getEnviosByCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(envioService.getEnviosByCorreo(correo));
    }

    @GetMapping("/fecha/{mes}")
    public ResponseEntity<List<Envio>> getEnviosByMes(@PathVariable int mes) {
        return ResponseEntity.ok(envioService.getEnviosByMes(mes));
    }
}
