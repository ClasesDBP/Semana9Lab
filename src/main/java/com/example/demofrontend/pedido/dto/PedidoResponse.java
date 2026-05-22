package com.example.demofrontend.pedido.dto;

import java.util.List;

public record PedidoResponse(
        Long id,
        Long usuarioId,
        String username,
        String estado,
        List<PedidoItemResponse> items
) {
}
