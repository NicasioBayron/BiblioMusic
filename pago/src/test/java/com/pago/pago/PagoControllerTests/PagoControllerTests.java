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
import static org.hamcrest.Matchers.*;

@WebMvcTest(PagoController.class)
@Import(PagoAssembler.class)
public class PagoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    @MockitoBean
    private PagoAssembler pagoAssembler;

    private ObjectMapper objectMapper;

    private PagoDTO pago1;
    private PagoDTO pago2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        pago1 = new PagoDTO();
        pago1.setIdPago(1L);
        pago1.setIdCarrito(10L);
        pago1.setTotal(5000.0);
        pago1.setMedioPago("Tarjeta");
        pago1.setConfirmacionPago("APROBADO");
        pago1.setFechaPago(LocalDate.of(2026, 6, 25));

        pago2 = new PagoDTO();
        pago2.setIdPago(2L);
        pago2.setIdCarrito(11L);
        pago2.setTotal(3000.0);
        pago2.setMedioPago("Webpay");
        pago2.setConfirmacionPago("APROBADO");
        pago2.setFechaPago(LocalDate.of(2026, 6, 26));
    }

    @Test
    void getAllPagos_returnsHateoasLinks() throws Exception {
        List<PagoDTO> lista = Arrays.asList(pago1, pago2);
        when(pagoService.getAllPagos()).thenReturn(lista);

        List<EntityModel<PagoDTO>> entityModels = Arrays.asList(EntityModel.of(pago1), EntityModel.of(pago2));
        when(pagoAssembler.toCollectionModel(any())).thenReturn(CollectionModel.of(entityModels));

        mockMvc.perform(get("/pago"))
                .andExpect(status().isOk());
    }

    @Test
    void getPagoById_returnsHateoasLinks() throws Exception {
        when(pagoService.getPagoById(1L)).thenReturn(pago1);
        when(pagoAssembler.toModel(any())).thenReturn(EntityModel.of(pago1));

        mockMvc.perform(get("/pago/1"))
                .andExpect(status().isOk());
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
        PagoDTO input = new PagoDTO();
        input.setIdCarrito(12L);
        input.setTotal(1500.0);
        input.setPrecioProducto(1500.0);
        input.setCantidad(1);
        input.setMedioPago("Efectivo");
        input.setConfirmacionPago("APROBADO");
        input.setFechaPago(LocalDate.of(2026, 6, 27));

        PagoDTO saved = new PagoDTO();
        saved.setIdPago(3L);
        saved.setIdCarrito(12L);
        saved.setTotal(1500.0);
        saved.setMedioPago("Efectivo");
        saved.setConfirmacionPago("APROBADO");
        saved.setFechaPago(LocalDate.of(2026, 6, 27));

        when(pagoService.crearPago(any(PagoDTO.class))).thenReturn(saved);
        when(pagoAssembler.toModel(any())).thenReturn(EntityModel.of(saved));

        mockMvc.perform(post("/pago")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarPago_returnsHateoasLinks() throws Exception {
        PagoDTO updated = new PagoDTO();
        updated.setIdPago(1L);
        updated.setIdCarrito(10L);
        updated.setTotal(5500.0);
        updated.setMedioPago("Tarjeta");
        updated.setConfirmacionPago("APROBADO");
        updated.setFechaPago(LocalDate.of(2026, 6, 25));

        when(pagoService.actualizarPago(eq(1L), any(PagoDTO.class))).thenReturn(updated);
        when(pagoAssembler.toModel(any())).thenReturn(EntityModel.of(updated));

        mockMvc.perform(put("/pago/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarPago_notFound() throws Exception {
        when(pagoService.actualizarPago(eq(1L), any(PagoDTO.class))).thenReturn(null);

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
        List<PagoDTO> lista = Arrays.asList(pago1, pago2);
        when(pagoService.getPagosByCorreo("test@test.com")).thenReturn(lista);

        List<EntityModel<PagoDTO>> entityModels = Arrays.asList(EntityModel.of(pago1), EntityModel.of(pago2));
        when(pagoAssembler.toCollectionModel(any())).thenReturn(CollectionModel.of(entityModels));

        mockMvc.perform(get("/pago/usuario/test@test.com"))
                .andExpect(status().isOk());
    }

    @Test
    void getPagosByMes_returnsHateoasLinks() throws Exception {
        List<PagoDTO> lista = Arrays.asList(pago1, pago2);
        when(pagoService.getPagosByMes(6)).thenReturn(lista);

        List<EntityModel<PagoDTO>> entityModels = Arrays.asList(EntityModel.of(pago1), EntityModel.of(pago2));
        when(pagoAssembler.toCollectionModel(any())).thenReturn(CollectionModel.of(entityModels));

        mockMvc.perform(get("/pago/fecha/6"))
                .andExpect(status().isOk());
    }
}