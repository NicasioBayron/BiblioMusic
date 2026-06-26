package com.cupones.cupones.service;

import org.springframework.stereotype.Service;

import com.cupones.cupones.model.Cupones;
import com.cupones.cupones.repository.CuponRepository;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CuponServices {

    private CuponRepository cuponRepository;

    public List<Cupones> listarCupones() {
        log.info("Obteniendo todas las cupones");
        return cuponRepository.findAll();
    }

    public Cupones buscarPorId(Long idCupon) {
        log.info("Obteniendo cupon con id: " + idCupon);
        return cuponRepository.findById(idCupon).get();
    }

    public Cupones crearCupon(Cupones cupon) {
        log.info("Creando cupon");
        return cuponRepository.save(cupon);
    }

    public Cupones actualizarCupon(Long idCupon, Cupones cupon) {
        log.info("Actualizando cupon con id: " + idCupon);
        Cupones cuponExistente = cuponRepository.findById(idCupon).get();
        cuponExistente.setPorcentajeDescuento(cupon.getPorcentajeDescuento());
        cuponExistente.setFechaExpiracion(cupon.getFechaExpiracion());
        cuponExistente.setActivo(cupon.getActivo());
        return cuponRepository.save(cuponExistente);
    }

    public void eliminarCupon(Long idCupon) {
        log.info("Eliminando cupon con id: " + idCupon);
        cuponRepository.deleteById(idCupon);
    }
}