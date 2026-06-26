package com.productos.productos.ProductosControllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productos.productos.controller.productoController;
import com.productos.productos.model.Producto;
import com.productos.productos.productoDTO.ProductoDTO;
import com.productos.productos.service.ProductoService;

@WebMvcTest(productoController.class)
public class ProductosControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    void testGetProductoById() throws Exception {
        when(productoService.getProductoById(1L)).thenReturn(producto);

        mockMvc.perform(get("/producto/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(ProductoDTO.fromModel(producto))));
    }

    @Test
    void testCrearProducto() throws Exception {
        when(productoService.crearProducto(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/producto")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productoDTO)))
            .andExpect(status().isOk())
            .andExpect(content().string("Producto creado exitosamente"));
    }

    @Test
    void testActualizarProducto() throws Exception {
        when(productoService.actualizarProducto(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(put("/producto")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productoDTO)))
            .andExpect(status().isOk())
            .andExpect(content().string("Producto actualizado exitosamente"));
    }
}

