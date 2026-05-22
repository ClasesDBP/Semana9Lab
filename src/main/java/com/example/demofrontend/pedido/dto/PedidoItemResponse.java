package com.example.demofrontend.pedido.dto;

import java.math.BigDecimal;

public record PedidoItemResponse(
        Long platoId,
        String nombrePlato,
        int cantidad,
        BigDecimal precioUnitario
) {
}
