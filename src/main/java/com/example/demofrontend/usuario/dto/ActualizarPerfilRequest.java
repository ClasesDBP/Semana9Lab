package com.example.demofrontend.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ActualizarPerfilRequest(
        @Email @NotBlank String correoElectronico,
        @NotBlank String password
) {
}
