package com.pago.pago.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Pago> getAllPagos() {
        return pagoRepository.findAll();
    }

    public Pago crearPago(Pago pago) {
        log.info("Calculando el total para el carrito...");
        // --- LOGICA OPCIÓN 2: CALCULO DIRECTO Y LOCAL ---
        // Asumiendo que tu modelo Pago tiene los atributos precioProducto y cantidad
        // Si no los tiene, se pueden pasar o calcular de forma fija para la simulación
        if (pago.getPrecioProducto() != null && pago.getCantidad() != null) {
            double totalCalculado = pago.getPrecioProducto() * pago.getCantidad();
            pago.setTotal(totalCalculado);
        } else {
            // Un valor por defecto por si acaso
            pago.setTotal(0.0);
        }
        // Se guarda en la base de datos con el total calculado automáticamente
        Pago savedPago = pagoRepository.save(pago);
        log.info("Pago guardado exitosamente en la base de datos.");
        return savedPago;
    }

    public Pago getPagoById(Long idPago) {
        Optional<Pago> pago = pagoRepository.findById(idPago);
        return pago.orElse(null);
    }

    public Pago actualizarPago(Long idPago, Pago pagoActualizado) {
        Optional<Pago> pagoExistente = pagoRepository.findById(idPago);

        if (pagoExistente.isPresent()) {
            Pago pago = pagoExistente.get();

            pago.setIdCarrito(pagoActualizado.getIdCarrito());
            pago.setTotal(pagoActualizado.getTotal());
            pago.setMedioPago(pagoActualizado.getMedioPago());
            pago.setConfirmacionPago(pagoActualizado.getConfirmacionPago());
            pago.setFechaPago(pagoActualizado.getFechaPago());

            return pagoRepository.save(pago);
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

    public List<Pago> getPagosByMes(int mes) {
        return pagoRepository.findByMes(mes);
    }

    public List<Pago> getPagosByCorreo(String correo) {
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
            return pagoRepository.findByIdCarritoIn(idCarritos);
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
}
