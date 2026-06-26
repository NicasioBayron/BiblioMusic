package com.pago.pago.PagoServiceTests;

import com.pago.pago.DTO.PagoDTO;
import com.pago.pago.model.Pago;
import com.pago.pago.repository.PagoRepository;
import com.pago.pago.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTests {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoEntidad;
    private PagoDTO pagoDtoInput;

    @BeforeEach
    void setUp() {
        pagoEntidad = new Pago(1L, 10L, 5000.0, 2500.0, 2, "Tarjeta", "APROBADO", LocalDate.of(2026, 6, 25));

        pagoDtoInput = new PagoDTO();
        pagoDtoInput.setIdPago(1L);
        pagoDtoInput.setIdCarrito(10L);
        pagoDtoInput.setTotal(5000.0);
        pagoDtoInput.setPrecioProducto(2500.0);
        pagoDtoInput.setCantidad(2);
        pagoDtoInput.setMedioPago("Tarjeta");
        pagoDtoInput.setConfirmacionPago("APROBADO");
        pagoDtoInput.setFechaPago(LocalDate.of(2026, 6, 25));
    }

    @Test
    void testGetAllPagos() {
        when(pagoRepository.findAll()).thenReturn(Arrays.asList(pagoEntidad));

        List<PagoDTO> list = pagoService.getAllPagos();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(5000.0, list.get(0).getTotal());
    }

    @Test
    void testCrearPago_calculatesTotal() {
        PagoDTO input = new PagoDTO();
        input.setIdCarrito(10L);
        input.setTotal(0.0);
        input.setPrecioProducto(1500.0);
        input.setCantidad(3);
        input.setMedioPago("Tarjeta");
        input.setConfirmacionPago("APROBADO");
        input.setFechaPago(LocalDate.of(2026, 6, 25));

        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        PagoDTO created = pagoService.crearPago(input);

        assertNotNull(created);
        assertEquals(4500.0, created.getTotal());
    }

    @Test
    void testGetPagoById() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoEntidad));

        PagoDTO found = pagoService.getPagoById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getIdPago());
    }

    @Test
    void testGetPagoById_NotFound() {
        when(pagoRepository.findById(2L)).thenReturn(Optional.empty());

        PagoDTO found = pagoService.getPagoById(2L);

        assertNull(found);
    }

    @Test
    void testActualizarPago() {
        PagoDTO updatedInfo = new PagoDTO();
        updatedInfo.setIdCarrito(11L);
        updatedInfo.setTotal(6000.0);
        updatedInfo.setMedioPago("Efectivo");
        updatedInfo.setConfirmacionPago("PENDIENTE");
        updatedInfo.setFechaPago(LocalDate.of(2026, 6, 25));

        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoEntidad));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        PagoDTO result = pagoService.actualizarPago(1L, updatedInfo);

        assertNotNull(result);
        assertEquals(6000.0, result.getTotal());
        assertEquals("Efectivo", result.getMedioPago());
        assertEquals("PENDIENTE", result.getConfirmacionPago());
    }

    @Test
    void testActualizarPago_NotFound() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.empty());

        PagoDTO result = pagoService.actualizarPago(1L, pagoDtoInput);

        assertNull(result);
    }

    @Test
    void testEliminarPago() {
        when(pagoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pagoRepository).deleteById(1L);

        boolean result = pagoService.eliminarPago(1L);

        assertTrue(result);
        verify(pagoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarPago_NotFound() {
        when(pagoRepository.existsById(1L)).thenReturn(false);

        boolean result = pagoService.eliminarPago(1L);

        assertFalse(result);
        verify(pagoRepository, never()).deleteById(anyLong());
    }
}