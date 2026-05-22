package com.example.demofrontend.plato.application;

import com.example.demofrontend.plato.domain.Plato;
import com.example.demofrontend.plato.dto.PlatoResponse;
import com.example.demofrontend.plato.repository.PlatoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlatoService {

    private final PlatoRepository platoRepository;

    public PlatoService(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    @Transactional(readOnly = true)
    public List<PlatoResponse> listarPlatos() {
        return platoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PlatoResponse> visualizarMenuDelDia() {
        return listarPlatos();
    }

    @Transactional(readOnly = true)
    public PlatoResponse obtenerDetalle(Long platoId) {
        Plato plato = obtenerEntidad(platoId);
        return toResponse(plato);
    }

    @Transactional(readOnly = true)
    public Plato obtenerEntidad(Long platoId) {
        return platoRepository.findById(platoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado"));
    }

    private PlatoResponse toResponse(Plato plato) {
        return new PlatoResponse(plato.getId(), plato.getNombre(), plato.getDescripcion(), plato.getPrecio());
    }
}
