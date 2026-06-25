package com.productos.productos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productos.productos.model.Producto;
import com.productos.productos.repository.ProductoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Iterable<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto crearProducto(Producto producto) {
        log.info("Creando nuevo producto: {}", producto.getNombre_producto());
        if (producto.getDetalles() != null) {
            for (com.productos.productos.model.detalle_producto detalle : producto.getDetalles()) {
                detalle.setProducto(producto);
            }
        }
        return productoRepository.save(producto);
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Producto actualizarProducto(Producto producto) {
        if (producto.getDetalles() != null) {
            for (com.productos.productos.model.detalle_producto detalle : producto.getDetalles()) {
                detalle.setProducto(producto);
            }
        }
        return productoRepository.save(producto);
    }
}
