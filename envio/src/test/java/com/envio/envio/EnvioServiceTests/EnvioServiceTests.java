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

    private Envio envio;

    @BeforeEach
    void setUp() {
        envio = new Envio(1L, 10L, 100L, 1L, "ENTREGADO", LocalDate.of(2026, 6, 25), "Dirección A");
    }

    @Test
    void testGetAllEnvios() {
        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio));
        List<Envio> list = envioService.getAllEnvios();
        assertEquals(1, list.size());
        assertEquals("ENTREGADO", list.get(0).getEstado_envio());
    }

    @Test
    void testCrearEnvio() {
        when(envioRepository.save(any(Envio.class))).thenReturn(envio);
        Envio created = envioService.crearEnvio(envio);
        assertNotNull(created);
        assertEquals(1L, created.getId_envio());
    }

    @Test
    void testGetEnvioById() {
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        Envio found = envioService.getEnvioById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getId_envio());
    }

    @Test
    void testGetEnvioById_NotFound() {
        when(envioRepository.findById(2L)).thenReturn(Optional.empty());
        Envio found = envioService.getEnvioById(2L);
        assertNull(found);
    }

    @Test
    void testActualizarEnvio() {
        Envio updatedInfo = new Envio(null, 15L, 100L, 2L, "EN_CAMINO", LocalDate.of(2026, 6, 25), "Nueva Dirección");
        when(envioRepository.findById(1L)).thenReturn(Optional.of(envio));
        when(envioRepository.save(any(Envio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Envio result = envioService.actualizarEnvio(1L, updatedInfo);

        assertNotNull(result);
        assertEquals("EN_CAMINO", result.getEstado_envio());
        assertEquals("Nueva Dirección", result.getDireccion_envio());
    }

    @Test
    void testActualizarEnvio_NotFound() {
        when(envioRepository.findById(1L)).thenReturn(Optional.empty());
        Envio result = envioService.actualizarEnvio(1L, envio);
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
