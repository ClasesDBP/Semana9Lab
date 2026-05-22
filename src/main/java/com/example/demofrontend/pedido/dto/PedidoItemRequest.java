package com.example.demofrontend.pedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PedidoItemRequest(
        @NotNull Long platoId,
        @Min(1) int cantidad
) {
}
