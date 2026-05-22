package com.example.demofrontend.plato.repository;

import com.example.demofrontend.plato.domain.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoRepository extends JpaRepository<Plato, Long> {
}
