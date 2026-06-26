package com.envio.envio.EnvioControllerTests;

import com.envio.envio.controller.EnvioController;
import com.envio.envio.DTO.EnvioDTO;
import com.envio.envio.service.EnvioService;
import com.envio.envio.assembler.EnvioAssembler;
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
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(EnvioController.class)
@Import(EnvioAssembler.class)
public class EnvioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnvioService envioService;

    private ObjectMapper objectMapper;

    private Envio envio1;
    private Envio envio2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        envio1 = new Envio(1L, 10L, 100L, 1L, "ENTREGADO", LocalDate.of(2026, 6, 25), "Dirección A");
        envio2 = new Envio(2L, 20L, 100L, 2L, "PENDIENTE", LocalDate.of(2026, 6, 26), "Dirección B");
    }

    @Test
    void getAllEnvios_returnsHateoasLinks() throws Exception {
        when(envioService.getAllEnvios()).thenReturn(Arrays.asList(envio1, envio2));

        mockMvc.perform(get("/envio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.envioList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.envioList[0].id_envio", is(1)))
                .andExpect(jsonPath("$._embedded.envioList[0]._links.self.href", containsString("/envio/1")))
                .andExpect(jsonPath("$._links.self.href", containsString("/envio")));
    }

    @Test
    void getEnvioById_returnsHateoasLinks() throws Exception {
        when(envioService.getEnvioById(1L)).thenReturn(envio1);

        mockMvc.perform(get("/envio/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_envio", is(1)))
                .andExpect(jsonPath("$._links.self.href", containsString("/envio/1")))
                .andExpect(jsonPath("$._links.envios.href", containsString("/envio")))
                .andExpect(jsonPath("$._links.delete.href", containsString("/envio/1")));
    }

    @Test
    void getEnvioById_notFound() throws Exception {
        when(envioService.getEnvioById(1L)).thenReturn(null);

        mockMvc.perform(get("/envio/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Envio no encontrado")));
    }

    @Test
    void crearEnvio_returnsSuccess() throws Exception {
        Envio input = new Envio(null, 30L, 100L, 3L, "PENDIENTE", LocalDate.of(2026, 6, 27), "Dirección C");
        Envio saved = new Envio(3L, 30L, 100L, 3L, "PENDIENTE", LocalDate.of(2026, 6, 27), "Dirección C");
        when(envioService.crearEnvio(any(Envio.class))).thenReturn(saved);

        mockMvc.perform(post("/envio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_envio", is(3)))
                .andExpect(jsonPath("$._links.self.href", containsString("/envio/3")));
    }

    @Test
    void actualizarEnvio_returnsHateoasLinks() throws Exception {
        Envio updated = new Envio(1L, 10L, 100L, 1L, "EN_CAMINO", LocalDate.of(2026, 6, 25), "Dirección Modificada");
        when(envioService.actualizarEnvio(eq(1L), any(Envio.class))).thenReturn(updated);

        mockMvc.perform(put("/envio/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_envio", is(1)))
                .andExpect(jsonPath("$.estado_envio", is("EN_CAMINO")))
                .andExpect(jsonPath("$._links.self.href", containsString("/envio/1")));
    }

    @Test
    void actualizarEnvio_notFound() throws Exception {
        when(envioService.actualizarEnvio(eq(1L), any(Envio.class))).thenReturn(null);

        mockMvc.perform(put("/envio/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarEnvio_returnsSuccess() throws Exception {
        when(envioService.eliminarEnvio(1L)).thenReturn(true);

        mockMvc.perform(delete("/envio/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Envío eliminado exitosamente")));
    }

    @Test
    void eliminarEnvio_notFound() throws Exception {
        when(envioService.eliminarEnvio(1L)).thenReturn(false);

        mockMvc.perform(delete("/envio/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getEnviosByCorreo_returnsHateoasLinks() throws Exception {
        when(envioService.getEnviosByCorreo("test@test.com")).thenReturn(Arrays.asList(envio1, envio2));

        mockMvc.perform(get("/envio/usuario/test@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.envioList", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", containsString("/envio/usuario/test%40test.com")));
    }

    @Test
    void getEnviosByMes_returnsHateoasLinks() throws Exception {
        when(envioService.getEnviosByMes(6)).thenReturn(Arrays.asList(envio1, envio2));

        mockMvc.perform(get("/envio/fecha/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.envioList", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", containsString("/envio/fecha/6")));
    }
}
