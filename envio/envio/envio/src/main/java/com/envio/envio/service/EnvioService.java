package com.envio.envio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.envio.envio.model.Envio;
import com.envio.envio.repository.EnvioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    public List<Envio> getAllEnvios() {
        return envioRepository.findAll();
    }

    public Envio crearEnvio(Envio envio) {
        log.info("Envio guardado exitosamente en la base de datos.");
        return envioRepository.save(envio);
    }

    public Envio getEnvioById(Long id) {
        Optional<Envio> envio = envioRepository.findById(id);
        return envio.orElse(null);
    }

    public Envio actualizarEnvio(Long id, Envio envioActualizado) {
        Optional<Envio> envioExistente = envioRepository.findById(id);

        if (envioExistente.isPresent()) {
            Envio envio = envioExistente.get();

            envio.setId_pago(envioActualizado.getId_pago());
            envio.setId_usuario(envioActualizado.getId_usuario());
            envio.setId_carrito(envioActualizado.getId_carrito());
            envio.setEstado_envio(envioActualizado.getEstado_envio());
            envio.setFecha_envio(envioActualizado.getFecha_envio());
            envio.setDireccion_envio(envioActualizado.getDireccion_envio());

            return envioRepository.save(envio);
        }

        return null;
    }

    public boolean eliminarEnvio(Long id) {
        if (envioRepository.existsById(id)) {
            envioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Autowired
    private org.springframework.web.client.RestTemplate restTemplate;

    public List<Envio> getEnviosByMes(int mes) {
        return envioRepository.findByMes(mes);
    }

    public List<Envio> getEnviosByCorreo(String correo) {
        try {
            // 1. Obtener usuario de login por correo, se comunica con el microservicio de
            // login
            log.debug("Conectando con el microservicio de login en el puerto 8080...");
            String loginUrl = "http://localhost:8080/api/auth/usuario/correo/" + correo;
            java.util.Map<String, Object> usuario = restTemplate.getForObject(loginUrl, java.util.Map.class);
            if (usuario == null || (!usuario.containsKey("id") && !usuario.containsKey("idUsuario"))) {
                return java.util.Collections.emptyList();
            }
            // Soporte tanto para "id" como "idUsuario" por si acaso
            Object idObj = usuario.containsKey("id") ? usuario.get("id") : usuario.get("idUsuario");
            Long idUsuario = Long.valueOf(idObj.toString());

            // 2. Obtener envios por idUsuario
            return envioRepository.findByIdUsuario(idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }
}