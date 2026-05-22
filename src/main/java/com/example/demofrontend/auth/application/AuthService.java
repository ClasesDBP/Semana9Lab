package com.example.demofrontend.auth.application;

import com.example.demofrontend.auth.dto.LoginRequest;
import com.example.demofrontend.auth.dto.LoginResponse;
import com.example.demofrontend.auth.dto.RegistroUsuarioRequest;
import com.example.demofrontend.usuario.domain.Usuario;
import com.example.demofrontend.usuario.dto.UsuarioResponse;
import com.example.demofrontend.usuario.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse registrar(RegistroUsuarioRequest request) {
        usuarioRepository.findByUsername(request.username()).ifPresent(usuario -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya existe");
        });
        usuarioRepository.findByCorreoElectronico(request.correoElectronico()).ifPresent(usuario -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo electronico ya existe");
        });

        Usuario usuario = new Usuario(
                null,
                request.username(),
                request.nombreCompleto(),
                request.correoElectronico(),
                passwordEncoder.encode(request.password())
        );
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas"));

        if (!passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
        }

        return new LoginResponse("Inicio de sesion exitoso", toResponse(usuario));
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
