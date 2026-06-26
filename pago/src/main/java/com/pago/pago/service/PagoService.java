package com.pago.pago.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pago.pago.DTO.PagoDTO;
import com.pago.pago.model.Pago;
import com.pago.pago.repository.PagoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private org.springframework.web.client.RestTemplate restTemplate;

    public List<PagoDTO> getAllPagos() {
        return pagoRepository.findAll().stream()
                .map(PagoDTO::fromModel)
                .collect(java.util.stream.Collectors.toList());
    }

    public PagoDTO crearPago(PagoDTO pagoDTO) {
        log.info("Calculando el total para el carrito...");
        Pago pago = pagoDTO.toModel();
        if (pago.getPrecioProducto() != null && pago.getCantidad() != null) {
            double totalCalculado = pago.getPrecioProducto() * pago.getCantidad();
            pago.setTotal(totalCalculado);
        } else {
            pago.setTotal(pago.getTotal());
        }
        Pago savedPago = pagoRepository.save(pago);
        log.info("Pago guardado exitosamente en la base de datos.");
        return PagoDTO.fromModel(savedPago);
    }

    public PagoDTO getPagoById(Long idPago) {
        Optional<Pago> pago = pagoRepository.findById(idPago);
        return pago.map(PagoDTO::fromModel).orElse(null);
    }

    public PagoDTO actualizarPago(Long idPago, PagoDTO pagoActualizado) {
        Optional<Pago> pagoExistente = pagoRepository.findById(idPago);

        if (pagoExistente.isPresent()) {
            Pago pago = pagoExistente.get();

            pago.setIdCarrito(pagoActualizado.getIdCarrito());
            if (pagoActualizado.getTotal() != null) {
                pago.setTotal(pagoActualizado.getTotal());
            }
            if (pagoActualizado.getPrecioProducto() != null && pagoActualizado.getCantidad() != null) {
                pago.setTotal(pagoActualizado.getPrecioProducto() * pagoActualizado.getCantidad());
            }
            pago.setMedioPago(pagoActualizado.getMedioPago());
            pago.setConfirmacionPago(pagoActualizado.getConfirmacionPago());
            pago.setFechaPago(pagoActualizado.getFechaPago());

            return PagoDTO.fromModel(pagoRepository.save(pago));
        }

        return null;
    }

    public boolean eliminarPago(Long idPago) {
        if (pagoRepository.existsById(idPago)) {
            pagoRepository.deleteById(idPago);
            return true;
        }
        return false;
    }

    public List<PagoDTO> getPagosByMes(int mes) {
        return pagoRepository.findByMes(mes).stream()
                .map(PagoDTO::fromModel)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<PagoDTO> getPagosByCorreo(String correo) {
        try {
            // 1. Obtener usuario de login por correo, se comunica con el microservicio de
            // login
            String loginUrl = "http://localhost:8080/api/auth/usuario/correo/" + correo;
            java.util.Map<String, Object> usuario = restTemplate.getForObject(loginUrl, java.util.Map.class);
            if (usuario == null || !usuario.containsKey("id")) {
                return java.util.Collections.emptyList();
            }
            Long idUsuario = Long.valueOf(usuario.get("id").toString());

            // 2. Obtener carritos de ese usuario, se comunica con el microservicio de
            // carrito
            log.debug("Conectando con el microservicio de Carrito en el puerto 8080...");
            String carritoUrl = "http://localhost:8080/carrito/usuario/" + idUsuario;
            java.util.Map[] carritos = restTemplate.getForObject(carritoUrl, java.util.Map[].class);
            if (carritos == null || carritos.length == 0) {
                return java.util.Collections.emptyList();
            }

            List<Long> idCarritos = new java.util.ArrayList<>();
            for (java.util.Map c : carritos) {
                if (c.containsKey("idCarrito")) {
                    idCarritos.add(Long.valueOf(c.get("idCarrito").toString()));
                }
            }

            if (idCarritos.isEmpty()) {
                return java.util.Collections.emptyList();
            }

            // 3. Obtener pagos por idCarrito, se comunica con el microservicio de pago
            return pagoRepository.findByIdCarritoIn(idCarritos).stream()
                    .map(PagoDTO::fromModel)
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
}
