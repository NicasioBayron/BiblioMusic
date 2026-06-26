package com.carrito.carrito.CarritoServiceTests;

import com.carrito.carrito.dto.CarritoDTO;
import com.carrito.carrito.model.Carrito;
import com.carrito.carrito.repository.CarritoRepository;
import com.carrito.carrito.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTests {

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    // Declaramos ambos tipos por separado para no cruzar los cables
    private Carrito entidadPrueba;
    private CarritoDTO dtoPrueba;

    @BeforeEach
    public void setUp() {
        // 1. La entidad real que maneja el repositorio en la base de datos
        entidadPrueba = new Carrito();
        entidadPrueba.setIdCarrito(1L);
        entidadPrueba.setIdUsuario(1L);

        // 2. El DTO representativo que viaja hacia/desde el servicio
        dtoPrueba = new CarritoDTO();
        dtoPrueba.setIdCarrito(1L);
        dtoPrueba.setIdUsuario(1L);
        dtoPrueba.setIdProducto(1L);
        dtoPrueba.setCantidadProducto(1);
    }

    @Test
    public void getAllCarritosTest() {
        // El repositorio devuelve la entidad real
        when(carritoRepository.findAll()).thenReturn(List.of(entidadPrueba));

        List<CarritoDTO> result = carritoService.getAllCarritos();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testGetCarritoById() {
        // El repositorio devuelve un Optional de la entidad real
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(entidadPrueba));

        CarritoDTO result = carritoService.getCarritoById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdCarrito());
    }

    @Test
    void testGetCarritoByUsuario() {
        // El repositorio devuelve la entidad real
        when(carritoRepository.findByIdUsuario(1L)).thenReturn(List.of(entidadPrueba));

        List<CarritoDTO> result = carritoService.getCarritosByUsuario(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testCrearCarrito() {
        // El repositorio guarda una entidad y retorna una entidad
        when(carritoRepository.save(any(Carrito.class))).thenReturn(entidadPrueba);

        // Al servicio le pasamos el DTO de entrada
        CarritoDTO result = carritoService.crearCarrito(dtoPrueba);

        assertNotNull(result);
        assertEquals(dtoPrueba.getIdCarrito(), result.getIdCarrito());
    }

    @Test
    void testActualizarCarrito() {
        // El repositorio devuelve la entidad original al buscarla
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(entidadPrueba));
        // El repositorio guarda la entidad modificada y devuelve la entidad actualizada
        when(carritoRepository.save(any(Carrito.class))).thenReturn(entidadPrueba);

        // Pasamos el DTO al servicio
        CarritoDTO result = carritoService.actualizarCarrito(1L, dtoPrueba);

        assertNotNull(result);
        assertEquals(dtoPrueba.getIdCarrito(), result.getIdCarrito());
    }

    @Test
    void testEliminarCarrito() {
        // Obligatorio: Hacer que el repositorio diga que SÍ existe para que pase el
        // "if" de tu servicio
        when(carritoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(carritoRepository).deleteById(1L);

        carritoService.eliminarCarrito(1L);

        // Ahora sí se va a verificar que se llamó al borrado
        verify(carritoRepository, times(1)).deleteById(1L);
    }
}