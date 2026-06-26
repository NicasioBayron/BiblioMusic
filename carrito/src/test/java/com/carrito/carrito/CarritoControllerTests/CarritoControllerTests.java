package com.carrito.carrito.CarritoControllerTests;

import com.carrito.carrito.controller.CarritoController;
import com.carrito.carrito.dto.CarritoDTO;
import com.carrito.carrito.service.CarritoService;
import com.carrito.carrito.assembler.CarritoAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CarritoController.class)
@Import(CarritoAssembler.class)
public class CarritoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarritoService carritoService;

    private ObjectMapper objectMapper;

    private CarritoDTO carrito1;
    private CarritoDTO carrito2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        carrito1 = new CarritoDTO();
        carrito1.setIdCarrito(1L);
        carrito1.setCantidadProducto(2);
        carrito1.setIdUsuario(100L);
        carrito1.setIdProducto(500L);

        carrito2 = new CarritoDTO();
        carrito2.setIdCarrito(2L);
        carrito2.setCantidadProducto(5);
        carrito2.setIdUsuario(100L);
        carrito2.setIdProducto(600L);
    }

    @Test
    void getAllCarritos_returnsHateoasLinks() throws Exception {
        when(carritoService.getAllCarritos()).thenReturn(Arrays.asList(carrito1, carrito2));

        mockMvc.perform(get("/carrito"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.carritoList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.carritoList[0].idCarrito", is(1)))
                .andExpect(jsonPath("$._embedded.carritoList[0]._links.self.href", containsString("/carrito/1")))
                .andExpect(jsonPath("$._links.self.href", containsString("/carrito")));
    }

    @Test
    void getCarritoById_returnsHateoasLinks() throws Exception {
        when(carritoService.getCarritoById(1L)).thenReturn(carrito1);

        mockMvc.perform(get("/carrito/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito", is(1)))
                .andExpect(jsonPath("$._links.self.href", containsString("/carrito/1")))
                .andExpect(jsonPath("$._links.carritos.href", containsString("/carrito")))
                .andExpect(jsonPath("$._links.delete.href", containsString("/carrito/1")));
    }

    @Test
    void getCarritoById_notFound() throws Exception {
        when(carritoService.getCarritoById(1L)).thenReturn(null);

        mockMvc.perform(get("/carrito/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Carrito no encontrado")));
    }

    @Test
    void getCarritosByUsuario_returnsHateoasLinks() throws Exception {
        when(carritoService.getCarritosByUsuario(100L)).thenReturn(Arrays.asList(carrito1, carrito2));

        mockMvc.perform(get("/carrito/usuario/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.carritoList", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", containsString("/carrito/usuario/100")));
    }

    @Test
    void crearCarrito_returnsSuccess() throws Exception {
        CarritoDTO input = new CarritoDTO();
        input.setCantidadProducto(3);
        input.setIdUsuario(100L);
        input.setIdProducto(700L);
        CarritoDTO saved = new CarritoDTO();
        saved.setIdCarrito(3L);
        saved.setCantidadProducto(3);
        saved.setIdUsuario(100L);
        saved.setIdProducto(700L);
        when(carritoService.crearCarrito(any(CarritoDTO.class))).thenReturn(saved);

        mockMvc.perform(post("/carrito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCarrito", is(3)))
                .andExpect(jsonPath("$._links.self.href", containsString("/carrito/3")));
    }

    @Test
    void actualizarCarrito_returnsHateoasLinks() throws Exception {
        CarritoDTO updated = new CarritoDTO();
        updated.setIdCarrito(1L);
        updated.setCantidadProducto(4);
        updated.setIdUsuario(100L);
        updated.setIdProducto(500L);
        when(carritoService.actualizarCarrito(eq(1L), any(CarritoDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/carrito/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCarrito", is(1)))
                .andExpect(jsonPath("$.cantidadProducto", is(4)))
                .andExpect(jsonPath("$._links.self.href", containsString("/carrito/1")));
    }

    @Test
    void actualizarCarrito_notFound() throws Exception {
        when(carritoService.actualizarCarrito(eq(1L), any(CarritoDTO.class))).thenReturn(null);

        mockMvc.perform(put("/carrito/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carrito1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarCarrito_returnsSuccess() throws Exception {
        when(carritoService.eliminarCarrito(1L)).thenReturn(true);

        mockMvc.perform(delete("/carrito/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Carrito eliminado exitosamente")));
    }

    @Test
    void eliminarCarrito_notFound() throws Exception {
        when(carritoService.eliminarCarrito(1L)).thenReturn(false);

        mockMvc.perform(delete("/carrito/1"))
                .andExpect(status().isNotFound());
    }
}
