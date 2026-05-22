package com.example.demofrontend.auth.dto;

import com.example.demofrontend.usuario.dto.UsuarioResponse;

public record LoginResponse(
        String mensaje,
        UsuarioResponse usuario
) {
}
