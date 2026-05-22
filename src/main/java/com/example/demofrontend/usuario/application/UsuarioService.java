package com.example.demofrontend.usuario.application;

import com.example.demofrontend.usuario.domain.Usuario;
import com.example.demofrontend.usuario.dto.ActualizarPerfilRequest;
import com.example.demofrontend.usuario.dto.UsuarioResponse;
import com.example.demofrontend.usuario.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse actualizarPerfil(Long usuarioId, ActualizarPerfilRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        usuarioRepository.findByCorreoElectronico(request.correoElectronico())
                .filter(existente -> !existente.getId().equals(usuarioId))
                .ifPresent(existente -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electronico ya existe");
                });

        usuario.setCorreoElectronico(request.correoElectronico());
        usuario.setPasswordHash(passwordEncoder.encode(request.password()));
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public Usuario obtenerEntidad(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getCorreoElectronico()
        );
    }
}
