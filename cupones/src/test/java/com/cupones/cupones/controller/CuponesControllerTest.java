package com.cupones.cupones.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.cupones.cupones.assembler.CuponesAssembler;
import com.cupones.cupones.model.Cupones;
import com.cupones.cupones.service.CuponServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(CuponController.class)
@Import(CuponesAssembler.class)
public class CuponesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CuponServices cuponServices;

    private ObjectMapper objectMapper;

    private Cupones cupon;
    private Cupones cuponResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        cuponResponse = new Cupones();
        cuponResponse.setIdCupon(1L);
        cuponResponse.setPorcentajeDescuento(15);
        cuponResponse.setActivo(true);
        cuponResponse.setFechaExpiracion(LocalDate.of(2026, 12, 31));

        cupon = new Cupones();
        cupon.setPorcentajeDescuento(15);
        cupon.setActivo(true);
        cupon.setFechaExpiracion(LocalDate.of(2026, 12, 31));
    }

    @Test
    public void testListarCupones() throws Exception {
        when(cuponServices.listarCupones()).thenReturn(List.of(cuponResponse));

        mockMvc.perform(get("/cupones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    public void testObtenerCupones() throws Exception {
        when(cuponServices.buscarPorId(1L)).thenReturn(cuponResponse);

        mockMvc.perform(get("/cupones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.porcentajeDescuento").value(15))
                .andExpect(jsonPath("$.activo").value(true))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    public void testCrearCupon() throws Exception {
        when(cuponServices.crearCupon(any(Cupones.class))).thenReturn(cuponResponse);

        mockMvc.perform(post("/cupones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cupon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.porcentajeDescuento").value(15))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    public void testActualizarCupon() throws Exception {
        when(cuponServices.actualizarCupon(eq(1L), any(Cupones.class))).thenReturn(cuponResponse);

        mockMvc.perform(put("/cupones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cupon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.porcentajeDescuento").value(15))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    public void testEliminarCupon() throws Exception {
        doNothing().when(cuponServices).eliminarCupon(1L);

        mockMvc.perform(delete("/cupones/1"))
                .andExpect(status().isNoContent());

        verify(cuponServices, times(1)).eliminarCupon(1L);
    }
}