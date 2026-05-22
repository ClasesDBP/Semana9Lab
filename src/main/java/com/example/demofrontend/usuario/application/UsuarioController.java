package com.example.demofrontend.usuario.application;

import com.example.demofrontend.usuario.dto.ActualizarPerfilRequest;
import com.example.demofrontend.usuario.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/{usuarioId}/perfil")
    public UsuarioResponse actualizarPerfil(
            @PathVariable Long usuarioId,
            @Valid @RequestBody ActualizarPerfilRequest request
    ) {
        return usuarioService.actualizarPerfil(usuarioId, request);
    }
}
