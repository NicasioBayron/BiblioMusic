package com.productos.productos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productos.productos.model.detalle_producto;
import com.productos.productos.repository.DetalleProductoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DetalleProductoService {

    @Autowired
    private DetalleProductoRepository detalleProductoRepository;

    public List<detalle_producto> getAllDetalles() {
        return detalleProductoRepository.findAll();
    }

    public detalle_producto crearDetalle(detalle_producto detalle) {
        return detalleProductoRepository.save(detalle);
    }

    public detalle_producto getDetalleById(Long id) {
        Optional<detalle_producto> detalle = detalleProductoRepository.findById(id);
        return detalle.orElse(null);
    }

    public boolean eliminarDetalle(Long id) {
        if (detalleProductoRepository.existsById(id)) {
            detalleProductoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public detalle_producto actualizarDetalle(detalle_producto detalle) {
        return detalleProductoRepository.save(detalle);
    }
}
