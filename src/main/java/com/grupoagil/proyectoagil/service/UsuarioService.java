package com.grupoagil.proyectoagil.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupoagil.proyectoagil.model.Usuario;
import com.grupoagil.proyectoagil.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para validar inicio de sesión
    public Optional<Usuario> iniciarSesion(String user, String password) {
        return usuarioRepository.findByUserAndPassword(user, password);
    }
}
