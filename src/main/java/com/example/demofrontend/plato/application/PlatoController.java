package com.example.demofrontend.plato.application;

import com.example.demofrontend.plato.dto.PlatoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService platoService;

    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    @GetMapping
    public List<PlatoResponse> listarPlatos() {
        return platoService.listarPlatos();
    }

    @GetMapping("/menu")
    public List<PlatoResponse> visualizarMenuDelDia() {
        return platoService.visualizarMenuDelDia();
    }

    @GetMapping("/{platoId}")
    public PlatoResponse obtenerDetalle(@PathVariable Long platoId) {
        return platoService.obtenerDetalle(platoId);
    }
}
