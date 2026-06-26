package com.envio.envio.EnvioControllerTests;

import com.envio.envio.DTO.EnvioDTO;
import com.envio.envio.service.EnvioService;
import com.envio.envio.assembler.EnvioAssembler;
import com.envio.envio.controller.EnvioController;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnvioController.class)
@Import(EnvioAssembler.class)
public class EnvioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EnvioService envioService;

    @MockitoBean
    private EnvioAssembler envioAssembler;

    private ObjectMapper objectMapper;
    private EnvioDTO envio1;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        envio1 = new EnvioDTO();
        envio1.setId_envio(1L);
        envio1.setId_pago(10L);
        envio1.setId_usuario(100L);
        envio1.setId_carrito(50L);
        envio1.setEstado_envio("En camino");
        envio1.setFecha_envio(LocalDate.of(2026, 6, 25));
        envio1.setDireccion_envio("Santiago Centro");
    }

    @Test
    public void testGetAllEnvios() throws Exception {
        List<EnvioDTO> lista = Arrays.asList(envio1);
        when(envioService.getAllEnvios()).thenReturn(lista);

        List<EntityModel<EnvioDTO>> entityModels = Arrays.asList(EntityModel.of(envio1));
        when(envioAssembler.toCollectionModel(any())).thenReturn(CollectionModel.of(entityModels));

        mockMvc.perform(get("/envio"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEnvioById() throws Exception {
        when(envioService.getEnvioById(1L)).thenReturn(envio1);
        when(envioAssembler.toModel(any())).thenReturn(EntityModel.of(envio1));

        mockMvc.perform(get("/envio/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEnvioById_NotFound() throws Exception {
        when(envioService.getEnvioById(1L)).thenReturn(null);

        mockMvc.perform(get("/envio/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCrearEnvio() throws Exception {
        when(envioService.crearEnvio(any(EnvioDTO.class))).thenReturn(envio1);
        when(envioAssembler.toModel(any())).thenReturn(EntityModel.of(envio1));

        mockMvc.perform(post("/envio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio1)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testActualizarEnvio() throws Exception {
        when(envioService.actualizarEnvio(eq(1L), any(EnvioDTO.class))).thenReturn(envio1);
        when(envioAssembler.toModel(any())).thenReturn(EntityModel.of(envio1));

        mockMvc.perform(put("/envio/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envio1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testEliminarEnvio_Exitoso() throws Exception {
        when(envioService.eliminarEnvio(1L)).thenReturn(true);

        mockMvc.perform(delete("/envio/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Envío eliminado exitosamente"));
    }

    @Test
    public void testEliminarEnvio_NotFound() throws Exception {
        when(envioService.eliminarEnvio(1L)).thenReturn(false);

        mockMvc.perform(delete("/envio/1"))
                .andExpect(status().isNotFound());
    }
}