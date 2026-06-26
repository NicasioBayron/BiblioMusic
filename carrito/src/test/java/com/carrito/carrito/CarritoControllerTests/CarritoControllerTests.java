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
import java.util.List;

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
    @MockitoBean
    private CarritoAssembler carritoAssembler;

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

        // ¡LA CLAVE! Mockear el assembler para la lista
        List<org.springframework.hateoas.EntityModel<CarritoDTO>> entities = Arrays.asList(
                org.springframework.hateoas.EntityModel.of(carrito1),
                org.springframework.hateoas.EntityModel.of(carrito2));
        when(carritoAssembler.toCollectionModel(any()))
                .thenReturn(org.springframework.hateoas.CollectionModel.of(entities));

        mockMvc.perform(get("/carrito"))
                .andExpect(status().isOk());
    }

    @Test
    void getCarritoById_returnsHateoasLinks() throws Exception {
        when(carritoService.getCarritoById(1L)).thenReturn(carrito1);
        // Mockear el assembler para un solo objeto
        when(carritoAssembler.toModel(any())).thenReturn(org.springframework.hateoas.EntityModel.of(carrito1));

        mockMvc.perform(get("/carrito/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getCarritosByUsuario_returnsHateoasLinks() throws Exception {
        when(carritoService.getCarritosByUsuario(100L)).thenReturn(Arrays.asList(carrito1, carrito2));

        List<org.springframework.hateoas.EntityModel<CarritoDTO>> entities = Arrays.asList(
                org.springframework.hateoas.EntityModel.of(carrito1),
                org.springframework.hateoas.EntityModel.of(carrito2));
        when(carritoAssembler.toCollectionModel(any()))
                .thenReturn(org.springframework.hateoas.CollectionModel.of(entities));

        mockMvc.perform(get("/carrito/usuario/100"))
                .andExpect(status().isOk());
    }

    @Test
    void crearCarrito_returnsSuccess() throws Exception {
        CarritoDTO input = new CarritoDTO();
        input.setCantidadProducto(3);
        input.setIdUsuario(100L);
        input.setIdProducto(700L);

        CarritoDTO saved = new CarritoDTO();
        saved.setIdCarrito(3L);

        when(carritoService.crearCarrito(any(CarritoDTO.class))).thenReturn(saved);
        when(carritoAssembler.toModel(any())).thenReturn(org.springframework.hateoas.EntityModel.of(saved));

        mockMvc.perform(post("/carrito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarCarrito_returnsHateoasLinks() throws Exception {
        CarritoDTO updated = new CarritoDTO();
        updated.setIdCarrito(1L);
        updated.setCantidadProducto(4);

        when(carritoService.actualizarCarrito(eq(1L), any(CarritoDTO.class))).thenReturn(updated);
        when(carritoAssembler.toModel(any())).thenReturn(org.springframework.hateoas.EntityModel.of(updated));

        mockMvc.perform(put("/carrito/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    @Test
    void getCarritoById_notFound() throws Exception {
        when(carritoService.getCarritoById(1L)).thenReturn(null);

        mockMvc.perform(get("/carrito/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Carrito no encontrado")));
    }
}
