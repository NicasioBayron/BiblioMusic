package com.cupones.cupones.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.cupones.cupones.model.Cupones;
import com.cupones.cupones.repository.CuponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CuponesServiceTest {

    @Mock
    private CuponRepository cuponRepository;

    @InjectMocks
    private CuponServices cuponService;

    private Cupones cuponModel;

    @BeforeEach
    void setUp() {

        cuponModel = new Cupones();
        cuponModel.setIdCupon(1L);
        cuponModel.setPorcentajeDescuento(15);
        cuponModel.setActivo(true);
        cuponModel.setFechaExpiracion(LocalDate.of(2026, 12, 31));
    }

    @Test
    void testListarCupones() {
        when(cuponRepository.findAll()).thenReturn(List.of(cuponModel));

        List<Cupones> result = cuponService.listarCupones();

        assertEquals(1, result.size());
        assertEquals(cuponModel.getIdCupon(), result.get(0).getIdCupon());
    }

    @Test
    void testBuscarPorId() {
        when(cuponRepository.findById(1L)).thenReturn(Optional.of(cuponModel));

        Cupones result = cuponService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdCupon());
    }

    @Test
    void testCrearCupon() {
        when(cuponRepository.save(any(Cupones.class))).thenReturn(cuponModel);

        Cupones result = cuponService.crearCupon(cuponModel);

        assertNotNull(result);
        assertEquals(cuponModel.getPorcentajeDescuento(), result.getPorcentajeDescuento());
    }

    @Test
    void testActualizarCupon() {
        Cupones cuponActualizado = new Cupones();
        cuponActualizado.setPorcentajeDescuento(20);
        cuponActualizado.setActivo(false);
        cuponActualizado.setFechaExpiracion(LocalDate.of(2026, 12, 31));

        when(cuponRepository.findById(1L)).thenReturn(Optional.of(cuponModel));
        when(cuponRepository.save(any(Cupones.class))).thenReturn(cuponActualizado);

        Cupones result = cuponService.actualizarCupon(1L, cuponActualizado);

        assertEquals(20, result.getPorcentajeDescuento());
        assertEquals(false, result.getActivo());
    }

    @Test
    void testEliminarCupon() {
        doNothing().when(cuponRepository).deleteById(1L);

        cuponService.eliminarCupon(1L);

        verify(cuponRepository, times(1)).deleteById(1L);
    }
}