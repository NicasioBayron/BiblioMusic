package com.login.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.login.model.Usuario;
import com.login.login.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j // logging
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private HashService hashService;

    public String login(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null)
            return null;
        // compare SHA-1 hashes
        String hashedInput = hashService.sha1(password);
        if (!hashedInput.equals(usuario.getPassword()))
            return null;

        return jwtService.generateToken(email);
    }

    public String getRole(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario.getRol();
    }

    public String register(String email, String password, String nombre, String apellido) {
        log.info("Registrando nuevo usuario...");
        Usuario existing = usuarioRepository.findByEmail(email);
        if (existing != null) {
            log.warn("El usuario ya existe.");
            return "Usuario ya existe!";
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(hashService.sha1(password));
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setRol("Usuario");

        usuarioRepository.save(usuario);
        log.info("Usuario guardado exitosamente en la base de datos.");

        return "Usuario creado exitosamente!";
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario updateUsuario(Long id, Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(id).orElse(null);
        if (existingUsuario == null) {
            return null;
        }

        existingUsuario.setEmail(usuario.getEmail());
        existingUsuario.setPassword(usuario.getPassword());
        existingUsuario.setNombre(usuario.getNombre());
        existingUsuario.setApellido(usuario.getApellido());
        existingUsuario.setRol(usuario.getRol());

        return usuarioRepository.save(existingUsuario);
    }

    public boolean deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
