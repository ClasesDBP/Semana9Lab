package com.example.demofrontend.pedido.application;

import com.example.demofrontend.pedido.domain.EstadoPedido;
import com.example.demofrontend.pedido.domain.Pedido;
import com.example.demofrontend.pedido.domain.PedidoItem;
import com.example.demofrontend.pedido.dto.CrearPedidoRequest;
import com.example.demofrontend.pedido.dto.PedidoItemRequest;
import com.example.demofrontend.pedido.dto.PedidoItemResponse;
import com.example.demofrontend.pedido.dto.PedidoResponse;
import com.example.demofrontend.pedido.repository.PedidoRepository;
import com.example.demofrontend.plato.application.PlatoService;
import com.example.demofrontend.plato.domain.Plato;
import com.example.demofrontend.usuario.application.UsuarioService;
import com.example.demofrontend.usuario.domain.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioService usuarioService;
    private final PlatoService platoService;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioService usuarioService, PlatoService platoService) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioService = usuarioService;
        this.platoService = platoService;
    }

    @Transactional
    public PedidoResponse crearPedido(CrearPedidoRequest request) {
        Usuario usuario = usuarioService.obtenerEntidad(request.usuarioId());
        List<PedidoItem> items = request.items().stream()
                .map(this::crearItem)
                .toList();

        Pedido pedido = new Pedido(null, usuario, items, EstadoPedido.PENDIENTE);
        return toResponse(pedidoRepository.save(pedido));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listarPorUsuario(Long usuarioId) {
        usuarioService.obtenerEntidad(usuarioId);
        return pedidoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PedidoResponse cancelarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        if (pedido.getEstado() == EstadoPedido.PREPARADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede cancelar un pedido ya preparado");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);
        return toResponse(pedidoRepository.save(pedido));
    }

    private PedidoItem crearItem(PedidoItemRequest itemRequest) {
        Plato plato = platoService.obtenerEntidad(itemRequest.platoId());
        return new PedidoItem(plato, itemRequest.cantidad());
    }

    private PedidoResponse toResponse(Pedido pedido) {
        List<PedidoItemResponse> items = pedido.getItems().stream()
                .map(item -> new PedidoItemResponse(
                        item.getPlato().getId(),
                        item.getPlato().getNombre(),
                        item.getCantidad(),
                        item.getPlato().getPrecio()
                ))
                .toList();

        return new PedidoResponse(
                pedido.getId(),
                pedido.getUsuario().getId(),
                pedido.getUsuario().getUsername(),
                pedido.getEstado().name(),
                items
        );
    }
}
