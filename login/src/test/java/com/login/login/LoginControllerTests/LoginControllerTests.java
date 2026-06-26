package com.login.login.LoginControllerTests;

import com.login.login.controller.UsuarioController;
import com.login.login.model.Usuario;
import com.login.login.service.UsuarioService;
import com.login.login.service.JwtService;
import com.login.login.assembler.UsuarioAssembler;
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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UsuarioController.class)
@Import(UsuarioAssembler.class)
public class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private JwtService jwtService;

    private ObjectMapper objectMapper;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("Juan");
        usuario1.setApellido("Perez");
        usuario1.setEmail("juan@perez.com");
        usuario1.setPassword("encoded");
        usuario1.setRol("User");

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("Admin");
        usuario2.setApellido("Soto");
        usuario2.setEmail("admin@soto.com");
        usuario2.setPassword("encoded");
        usuario2.setRol("Admin");
    }

    @Test
    void getUsuarios_unauthorized_tokenMissing() throws Exception {
        mockMvc.perform(get("/api/auth/usuarios"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Token inválido o no proporcionado")));
    }

    @Test
    void getUsuarios_forbidden_notAdmin() throws Exception {
        when(jwtService.isValid("user-token")).thenReturn(true);
        when(jwtService.getEmailFromToken("user-token")).thenReturn("juan@perez.com");
        when(usuarioService.getRole("juan@perez.com")).thenReturn("User");

        mockMvc.perform(get("/api/auth/usuarios")
                        .header("Authorization", "user-token"))
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("Acceso denegado: Se requiere rol de Admin")));
    }

    @Test
    void getUsuarios_authorized_returnsHateoasLinks() throws Exception {
        when(jwtService.isValid("admin-token")).thenReturn(true);
        when(jwtService.getEmailFromToken("admin-token")).thenReturn("admin@soto.com");
        when(usuarioService.getRole("admin@soto.com")).thenReturn("Admin");
        when(usuarioService.getAllUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2));

        mockMvc.perform(get("/api/auth/usuarios")
                        .header("Authorization", "admin-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.usuarioList[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.usuarioList[0]._links.self.href", containsString("/api/auth/usuarios/1")))
                .andExpect(jsonPath("$._links.self.href", containsString("/api/auth/usuarios")));
    }

    @Test
    void getUsuarioById_returnsHateoasLinks() throws Exception {
        when(jwtService.isValid("admin-token")).thenReturn(true);
        when(jwtService.getEmailFromToken("admin-token")).thenReturn("admin@soto.com");
        when(usuarioService.getRole("admin@soto.com")).thenReturn("Admin");
        when(usuarioService.getUsuarioById(1L)).thenReturn(usuario1);

        mockMvc.perform(get("/api/auth/usuarios/1")
                        .header("Authorization", "admin-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$._links.self.href", containsString("/api/auth/usuarios/1")))
                .andExpect(jsonPath("$._links.usuarios.href", containsString("/api/auth/usuarios")))
                .andExpect(jsonPath("$._links.delete.href", containsString("/api/auth/usuarios/1")));
    }

    @Test
    void getUsuarioByCorreo_returnsHateoasLinks() throws Exception {
        when(usuarioService.getUsuarioByEmail("juan@perez.com")).thenReturn(usuario1);

        mockMvc.perform(get("/api/auth/usuario/correo/juan@perez.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$._links.self.href", containsString("/api/auth/usuarios/1")));
    }

    @Test
    void updateUsuario_returnsHateoasLinks() throws Exception {
        when(jwtService.isValid("admin-token")).thenReturn(true);
        when(jwtService.getEmailFromToken("admin-token")).thenReturn("admin@soto.com");
        when(usuarioService.getRole("admin@soto.com")).thenReturn("Admin");

        Usuario updated = new Usuario();
        updated.setId(1L);
        updated.setNombre("Juan Modificado");
        updated.setApellido("Perez");
        updated.setEmail("juan@perez.com");
        updated.setRol("User");

        when(usuarioService.updateUsuario(eq(1L), any(Usuario.class))).thenReturn(updated);

        mockMvc.perform(put("/api/auth/usuarios/1")
                        .header("Authorization", "admin-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Juan Modificado")))
                .andExpect(jsonPath("$._links.self.href", containsString("/api/auth/usuarios/1")));
    }
}
