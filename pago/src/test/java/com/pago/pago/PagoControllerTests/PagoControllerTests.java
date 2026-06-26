package com.pago.pago.PagoControllerTests;

import com.pago.pago.controller.PagoController;
import com.pago.pago.DTO.PagoDTO;
import com.pago.pago.service.PagoService;
import com.pago.pago.assembler.PagoAssembler;
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

@WebMvcTest(PagoController.class)
@Import(PagoAssembler.class)
public class PagoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    private ObjectMapper objectMapper;

    private Pago pago1;
    private Pago pago2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        pago1 = new Pago(1L, 10L, 5000.0, null, null, "Tarjeta", "APROBADO", LocalDate.of(2026, 6, 25));
        pago2 = new Pago(2L, 11L, 3000.0, null, null, "Webpay", "APROBADO", LocalDate.of(2026, 6, 26));
    }

    @Test
    void getAllPagos_returnsHateoasLinks() throws Exception {
        when(pagoService.getAllPagos()).thenReturn(Arrays.asList(pago1, pago2));

        mockMvc.perform(get("/pago"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pagoList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.pagoList[0].idPago", is(1)))
                .andExpect(jsonPath("$._embedded.pagoList[0]._links.self.href", containsString("/pago/1")))
                .andExpect(jsonPath("$._links.self.href", containsString("/pago")));
    }

    @Test
    void getPagoById_returnsHateoasLinks() throws Exception {
        when(pagoService.getPagoById(1L)).thenReturn(pago1);

        mockMvc.perform(get("/pago/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPago", is(1)))
                .andExpect(jsonPath("$._links.self.href", containsString("/pago/1")))
                .andExpect(jsonPath("$._links.pagos.href", containsString("/pago")))
                .andExpect(jsonPath("$._links.delete.href", containsString("/pago/1")));
    }

    @Test
    void getPagoById_notFound() throws Exception {
        when(pagoService.getPagoById(1L)).thenReturn(null);

        mockMvc.perform(get("/pago/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Pago no encontrado")));
    }

    @Test
    void crearPago_returnsSuccess() throws Exception {
        Pago input = new Pago(null, 12L, 1500.0, 1500.0, 1, "Efectivo", "APROBADO", LocalDate.of(2026, 6, 27));
        Pago saved = new Pago(3L, 12L, 1500.0, null, null, "Efectivo", "APROBADO", LocalDate.of(2026, 6, 27));
        when(pagoService.crearPago(any(Pago.class))).thenReturn(saved);

        mockMvc.perform(post("/pago")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPago", is(3)))
                .andExpect(jsonPath("$._links.self.href", containsString("/pago/3")));
    }

    @Test
    void actualizarPago_returnsHateoasLinks() throws Exception {
        Pago updated = new Pago(1L, 10L, 5500.0, null, null, "Tarjeta", "APROBADO", LocalDate.of(2026, 6, 25));
        when(pagoService.actualizarPago(eq(1L), any(Pago.class))).thenReturn(updated);

        mockMvc.perform(put("/pago/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPago", is(1)))
                .andExpect(jsonPath("$.total", is(5500.0)))
                .andExpect(jsonPath("$._links.self.href", containsString("/pago/1")));
    }

    @Test
    void actualizarPago_notFound() throws Exception {
        when(pagoService.actualizarPago(eq(1L), any(Pago.class))).thenReturn(null);

        mockMvc.perform(put("/pago/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pago1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarPago_returnsSuccess() throws Exception {
        when(pagoService.eliminarPago(1L)).thenReturn(true);

        mockMvc.perform(delete("/pago/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pago eliminado exitosamente")));
    }

    @Test
    void eliminarPago_notFound() throws Exception {
        when(pagoService.eliminarPago(1L)).thenReturn(false);

        mockMvc.perform(delete("/pago/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPagosByCorreo_returnsHateoasLinks() throws Exception {
        when(pagoService.getPagosByCorreo("test@test.com")).thenReturn(Arrays.asList(pago1, pago2));

        mockMvc.perform(get("/pago/usuario/test@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pagoList", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", containsString("/pago/usuario/test%40test.com")));
    }

    @Test
    void getPagosByMes_returnsHateoasLinks() throws Exception {
        when(pagoService.getPagosByMes(6)).thenReturn(Arrays.asList(pago1, pago2));

        mockMvc.perform(get("/pago/fecha/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pagoList", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", containsString("/pago/fecha/6")));
    }
}
