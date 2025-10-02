package com.grupoagil.proyectoagil.controller;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupoagil.proyectoagil.model.Usuario;
import com.grupoagil.proyectoagil.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Iniciar sesión
   @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String user, @RequestParam String password) {
        Optional<Usuario> usuario = usuarioService.iniciarSesion(user, password);

        if (usuario.isPresent()) {
            return ResponseEntity.ok("Inicio de sesión exitoso. Bienvenido " + usuario.get().getNombre());
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}