package com.carrito.carrito.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carrito.carrito.dto.CarritoDTO;
import com.carrito.carrito.model.Carrito;
import com.carrito.carrito.repository.CarritoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    public List<CarritoDTO> getAllCarritos() {
        return carritoRepository.findAll().stream()
                .map(CarritoDTO::fromModel)
                .collect(Collectors.toList());
    }

    public CarritoDTO crearCarrito(CarritoDTO carritoDTO) {
        log.info("Creando nuevo carrito para el usuario ID: {}", carritoDTO.getIdUsuario());
        Carrito carrito = carritoRepository.save(carritoDTO.toModel());
        return CarritoDTO.fromModel(carrito);
    }

    public CarritoDTO getCarritoById(Long id) {
        Optional<Carrito> carrito = carritoRepository.findById(id);
        return carrito.map(CarritoDTO::fromModel).orElse(null);
    }

    public List<CarritoDTO> getCarritosByUsuario(Long idUsuario) {
        return carritoRepository.findByIdUsuario(idUsuario).stream()
                .map(CarritoDTO::fromModel)
                .collect(Collectors.toList());
    }

    public CarritoDTO actualizarCarrito(Long id, CarritoDTO carritoActualizado) {
        Optional<Carrito> carritoExistente = carritoRepository.findById(id);

        if (carritoExistente.isPresent()) {
            Carrito carrito = carritoExistente.get();

            carrito.setCantidadProducto(carritoActualizado.getCantidadProducto());
            carrito.setIdUsuario(carritoActualizado.getIdUsuario());
            carrito.setIdProducto(carritoActualizado.getIdProducto());

            return CarritoDTO.fromModel(carritoRepository.save(carrito));
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