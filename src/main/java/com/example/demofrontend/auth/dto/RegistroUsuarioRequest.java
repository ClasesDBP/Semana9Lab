package com.example.demofrontend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistroUsuarioRequest(
        @NotBlank String username,
        @NotBlank String nombreCompleto,
        @Email @NotBlank String correoElectronico,
        @NotBlank String password
) {
}
