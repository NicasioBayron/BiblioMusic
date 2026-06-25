package com.carrito.carrito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrito.carrito.model.Carrito;
import com.carrito.carrito.repository.CarritoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    public List<Carrito> getAllCarritos() {
        return carritoRepository.findAll();
    }

    public Carrito crearCarrito(Carrito carrito) {
        log.info("Creando nuevo carrito para el usuario ID: {}", carrito.getIdUsuario());
        return carritoRepository.save(carrito);
    }

    public Carrito getCarritoById(Long id) {
        Optional<Carrito> carrito = carritoRepository.findById(id);
        return carrito.orElse(null);
    }

    public List<Carrito> getCarritosByUsuario(Long idUsuario) {
        return carritoRepository.findByIdUsuario(idUsuario);
    }

    public Carrito actualizarCarrito(Long id, Carrito carritoActualizado) {
        Optional<Carrito> carritoExistente = carritoRepository.findById(id);

        if (carritoExistente.isPresent()) {
            Carrito carrito = carritoExistente.get();

            carrito.setCantidadProducto(carritoActualizado.getCantidadProducto());
            carrito.setIdUsuario(carritoActualizado.getIdUsuario());
            carrito.setIdProducto(carritoActualizado.getIdProducto());

            return carritoRepository.save(carrito);
        }

        return null;
    }

    public boolean eliminarCarrito(Long id) {
        if (carritoRepository.existsById(id)) {
            carritoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}