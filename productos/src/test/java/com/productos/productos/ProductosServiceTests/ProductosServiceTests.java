package com.productos.productos.ProductosServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.productos.productos.model.Producto;
import com.productos.productos.productoDTO.ProductoDTO;
import com.productos.productos.repository.ProductoRepository;
import com.productos.productos.service.ProductoService;

@ExtendWith(MockitoExtension.class)
public class ProductosServiceTests {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId_producto(1L);
        producto.setNombre_producto("Libro Test");
        producto.setTipo_producto("Física");
        producto.setPrecio(150.0);
        producto.setStock(10);

        productoDTO = ProductoDTO.builder()
                .id_producto(1L)
                .nombre_producto("Libro Test")
                .tipo_producto("Física")
                .precio(150.0)
                .stock(10)
                .build();
    }

    @Test
    void testGetProductoById() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto result = productoService.getProductoById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId_producto());
    }

    @Test
    void testCrearProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto result = productoService.crearProducto(productoDTO.toModel());

        assertNotNull(result);
        assertEquals(producto.getNombre_producto(), result.getNombre_producto());
    }

    @Test
    void testActualizarProducto() {
        // CORRECCIÓN: Devolver dinámicamente el argumento recibido en el save
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ProductoDTO updatedDto = ProductoDTO.builder()
                .id_producto(1L)
                .nombre_producto("Libro Editado")
                .tipo_producto("Física")
                .precio(175.0)
                .stock(12)
                .build();

        Producto result = productoService.actualizarProducto(updatedDto.toModel());

        assertNotNull(result);
        assertEquals(updatedDto.getNombre_producto(), result.getNombre_producto());
        assertEquals(updatedDto.getPrecio(), result.getPrecio());
    }
}
