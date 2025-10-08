package com.grupoagil.proyectoagil.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoagil.proyectoagil.model.Usuario;
import com.grupoagil.proyectoagil.service.UsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> datosLogin) {
        String user = datosLogin.get("user");
        String password = datosLogin.get("password");

        Optional<Usuario> usuario = usuarioService.iniciarSesion(user, password);

        Map<String, Object> respuesta = new HashMap<>();

        if (usuario.isPresent()) {
            Usuario usuarioObj = usuario.get();
            respuesta.put("exito", true);
            respuesta.put("mensaje", "Inicio de sesión exitoso. Bienvenido " + usuarioObj.getNombre());
        } else {
            respuesta.put("exito", false);
            respuesta.put("mensaje", "Credenciales inválidas");
        }

        return ResponseEntity.ok(respuesta);
    }
}
