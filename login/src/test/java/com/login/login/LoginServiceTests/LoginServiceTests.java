package com.login.login.LoginServiceTests;

import com.login.login.model.Usuario;
import com.login.login.repository.UsuarioRepository;
import com.login.login.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan@perez.com");
        usuario.setPassword("pass");
        usuario.setRol("User");
    }

    @Test
    void testGetUsuarioById() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Usuario found = usuarioService.getUsuarioById(1L);
        assertNotNull(found);
        assertEquals("Juan", found.getNombre());
    }

    @Test
    void testGetUsuarioById_NotFound() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());
        Usuario found = usuarioService.getUsuarioById(2L);
        assertNull(found);
    }
}
