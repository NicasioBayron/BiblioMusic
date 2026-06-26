package com.resenas.resenas.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.resenas.resenas.assembler.ResenasAssembler;
import com.resenas.resenas.model.Resenas;
import com.resenas.resenas.service.ResenasService;
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

import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(ResenasController.class)
@Import(ResenasAssembler.class)
public class ResenasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenasService resenasService;

    private ObjectMapper objectMapper;

    private Resenas resena;
    private Resenas resenaResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        resenaResponse = new Resenas();
        resenaResponse.setIdResena(1L);
        resenaResponse.setIdProducto(100L);
        resenaResponse.setIdUsuario(50L);
        resenaResponse.setCalificacion(5);
        resenaResponse.setComentario("Me encantó");
        resenaResponse.setFechaCreacion(LocalDateTime.now());
        resena = new Resenas();
        resena.setIdProducto(100L);
        resena.setIdUsuario(50L);
        resena.setCalificacion(5);
        resena.setComentario("Me encantó");
    }

    @Test
    public void testListarResenas() throws Exception {
        when(resenasService.listarResenas()).thenReturn(List.of(resenaResponse));

        mockMvc.perform(get("/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded").exists())
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    public void testObtenerResena() throws Exception {
        when(resenasService.buscarPorId(1L)).thenReturn(resenaResponse);

        mockMvc.perform(get("/resenas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.comentario").value("Me encantó"))
                .andExpect(jsonPath("$._links.self").exists());
    }

    @Test
    public void testCrearResena() throws Exception {
        when(resenasService.crearResena(any(Resenas.class))).thenReturn(resenaResponse);

        mockMvc.perform(post("/resenas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resena)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.comentario").value("Me encantó"));
    }

    @Test
    public void testActualizarResena() throws Exception {
        when(resenasService.actualizarResena(eq(1L), any(Resenas.class))).thenReturn(resenaResponse);

        mockMvc.perform(put("/resenas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resena)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calificacion").value(5))
                .andExpect(jsonPath("$.comentario").value("Me encantó"));
    }

    @Test
    public void testEliminarResena() throws Exception {
        when(resenasService.eliminarResena(1L)).thenReturn(true);

        mockMvc.perform(delete("/resenas/1"))
                .andExpect(status().isNoContent());

        verify(resenasService, times(1)).eliminarResena(1L);
    }
}