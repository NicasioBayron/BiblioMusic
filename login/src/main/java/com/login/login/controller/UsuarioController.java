package com.login.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.login.login.service.UsuarioService;
import com.login.login.DTO.LoginDTO;
import com.login.login.DTO.RegisterDTO;
import com.login.login.service.JwtService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import com.login.login.assembler.UsuarioAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.login.login.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioAssembler assembler;

    @PostMapping("/login")
    public java.util.Map<String, String> login(@RequestBody LoginDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String token = usuarioService.login(email, password);

        java.util.Map<String, String> resp = new java.util.HashMap<>();
        if (token == null) {
            resp.put("status", "error");
            resp.put("token", "");
        } else {
            resp.put("status", "Sesion Iniciada Correctamente");
            resp.put("su Token de Acceso es", token);
        }
        return resp;
    }

    @PostMapping("/register")
    public java.util.Map<String, String> register(@RequestBody RegisterDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();
        String nombre = request.getNombre();
        String apellido = request.getApellido();
        String resultado = usuarioService.register(email, password, nombre, apellido);

        java.util.Map<String, String> resp = new java.util.HashMap<>();
        resp.put("message", resultado);
        return resp;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> getUsuarios(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !jwtService.isValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o no proporcionado");
        }

        String email = jwtService.getEmailFromToken(token);
        String rol = usuarioService.getRole(email);

        if (!"Admin".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Se requiere rol de Admin");
        }

        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        CollectionModel<EntityModel<Usuario>> model = assembler.toCollectionModel(usuarios);
        model.add(linkTo(methodOn(UsuarioController.class).getUsuarios(token)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id,
            @RequestBody com.login.login.model.Usuario usuario,
            @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !jwtService.isValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o no proporcionado");
        }
        String email = jwtService.getEmailFromToken(token);
        String rol = usuarioService.getRole(email);
        if (!"Admin".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Se requiere rol de Admin");
        }
        com.login.login.model.Usuario updatedUsuario = usuarioService.updateUsuario(id, usuario);
        if (updatedUsuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Map.of("error", "Usuario no encontrado"));
        }
        return ResponseEntity.ok(assembler.toModel(updatedUsuario));
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !jwtService.isValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o no proporcionado");
        }

        String email = jwtService.getEmailFromToken(token);
        String rol = usuarioService.getRole(email);

        if (!"Admin".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Se requiere rol de Admin");
        }

        com.login.login.model.Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Map.of("error", "Usuario no encontrado"));
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @GetMapping("/usuario/correo/{correo}")
    public ResponseEntity<?> getUsuarioByCorreo(@PathVariable String correo) {
        // No pedimos token aquí para facilitar la comunicación interna entre
        // microservicios,
        // o se puede asegurar a nivel de gateway.
        com.login.login.model.Usuario usuario = usuarioService.getUsuarioByEmail(correo);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Map.of("error", "Usuario no encontrado"));
        }
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !jwtService.isValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido o no proporcionado");
        }

        String email = jwtService.getEmailFromToken(token);
        String rol = usuarioService.getRole(email);

        if (!"Admin".equalsIgnoreCase(rol)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Se requiere rol de Admin");
        }

        boolean deleted = usuarioService.deleteUsuario(id);
        if (deleted) {
            return ResponseEntity.ok(java.util.Map.of("message", "Usuario eliminado correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(java.util.Map.of("error", "Usuario no encontrado"));
        }
    }
}
