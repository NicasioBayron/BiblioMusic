package com.envio.envio.EnvioServiceTests;

import com.envio.envio.DTO.EnvioDTO;
import com.envio.envio.model.Envio;
import com.envio.envio.repository.EnvioRepository;
import com.envio.envio.service.EnvioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnvioServiceTests {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    private Envio envioEntidad;
    private EnvioDTO envioDtoInput;

    @BeforeEach
    void setUp() {
        envioEntidad = new Envio();
        envioEntidad.setId_envio(1L);
        envioEntidad.setId_pago(10L);
        envioEntidad.setId_usuario(100L);
        envioEntidad.setId_carrito(1L);
        envioEntidad.setEstado_envio("ENTREGADO");
        envioEntidad.setFecha_envio(LocalDate.of(2026, 6, 25));
        envioEntidad.setDireccion_envio("Dirección A");

        envioDtoInput = new EnvioDTO();
        envioDtoInput.setId_envio(1L);
        envioDtoInput.setId_pago(10L);
        envioDtoInput.setId_usuario(100L);
        envioDtoInput.setId_carrito(1L);
        envioDtoInput.setEstado_envio("ENTREGADO");
        envioDtoInput.setFecha_envio(LocalDate.of(2026, 6, 25));
        envioDtoInput.setDireccion_envio("Dirección A");
    }

    @Test
    void testGetAllEnvios() {
        when(envioRepository.findAll()).thenReturn(Arrays.asList(envioEntidad));

        List<EnvioDTO> list = envioService.getAllEnvios();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("ENTREGADO", list.get(0).getEstado_envio());
    }

    @Test
    void testCrearEnvio() {
        when(envioRepository.save(any(Envio.class))).thenReturn(envioEntidad);

        EnvioDTO created = envioService.crearEnvio(envioDtoInput);

        assertNotNull(created);
        assertEquals(1L, created.getId_envio());
    }

    @Test
    void testGetEnvioById() {
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envioEntidad));

        EnvioDTO found = envioService.getEnvioById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId_envio());
    }

    @Test
    void testGetEnvioById_NotFound() {
        when(envioRepository.findById(2L)).thenReturn(Optional.empty());

        EnvioDTO found = envioService.getEnvioById(2L);

        assertNull(found);
    }

    @Test
    void testActualizarEnvio() {
        EnvioDTO updatedDtoInfo = new EnvioDTO();
        updatedDtoInfo.setId_pago(15L);
        updatedDtoInfo.setId_usuario(100L);
        updatedDtoInfo.setId_carrito(2L);
        updatedDtoInfo.setEstado_envio("EN_CAMINO");
        updatedDtoInfo.setFecha_envio(LocalDate.of(2026, 6, 25));
        updatedDtoInfo.setDireccion_envio("Nueva Dirección");

        when(envioRepository.findById(1L)).thenReturn(Optional.of(envioEntidad));
        // RETORNO DINÁMICO: Devuelve el objeto modificado que el método save() recibe
        // por parámetro
        when(envioRepository.save(any(Envio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EnvioDTO result = envioService.actualizarEnvio(1L, updatedDtoInfo);

        assertNotNull(result);
        assertEquals("EN_CAMINO", result.getEstado_envio());
        assertEquals("Nueva Dirección", result.getDireccion_envio());
    }

    @Test
    void testActualizarEnvio_NotFound() {
        when(envioRepository.findById(1L)).thenReturn(Optional.empty());

        EnvioDTO result = envioService.actualizarEnvio(1L, envioDtoInput);

        assertNull(result);
    }

    @Test
    void testEliminarEnvio() {
        when(envioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(envioRepository).deleteById(1L);

        boolean result = envioService.eliminarEnvio(1L);

        assertTrue(result);
        verify(envioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarEnvio_NotFound() {
        when(envioRepository.existsById(1L)).thenReturn(false);

        boolean result = envioService.eliminarEnvio(1L);

        assertFalse(result);
        verify(envioRepository, never()).deleteById(anyLong());
    }
}