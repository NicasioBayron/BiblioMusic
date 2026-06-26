package com.productos.productos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.productos.productos.model.Producto;
import com.productos.productos.productoDTO.ProductoDTO;
import com.productos.productos.service.ProductoService;

@RestController
@RequestMapping("/producto")
public class productoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> getAllProductos() {
        Iterable<Producto> productos = productoService.getAllProductos();
        List<ProductoDTO> respuesta = new ArrayList<>();
        productos.forEach(producto -> respuesta.add(ProductoDTO.fromModel(producto)));
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO productoDto) {
        productoService.crearProducto(productoDto.toModel());
        return ResponseEntity.ok("Producto creado exitosamente");
    }

    @PutMapping
    public ResponseEntity<?> actualizarProducto(@RequestBody ProductoDTO productoDto) {
        productoService.actualizarProducto(productoDto.toModel());
        return ResponseEntity.ok("Producto actualizado exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProducto(@PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
        return ResponseEntity.ok(ProductoDTO.fromModel(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        if (productoService.eliminarProducto(id)) {
            return ResponseEntity.ok("Producto eliminado exitosamente");
        }
        return ResponseEntity.notFound().build();
    }
}
