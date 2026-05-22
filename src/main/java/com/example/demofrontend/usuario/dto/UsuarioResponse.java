package com.example.demofrontend.usuario.dto;

public record UsuarioResponse(
        Long id,
        String username,
        String nombreCompleto,
        String correoElectronico
) {
}
