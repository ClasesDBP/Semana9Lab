package com.example.demofrontend.config;

import com.example.demofrontend.plato.domain.Plato;
import com.example.demofrontend.plato.repository.PlatoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class PlatoDataInitializer {

    @Bean
    CommandLineRunner initPlatos(PlatoRepository platoRepository) {
        return args -> {
            if (platoRepository.count() > 0) {
                return;
            }

            platoRepository.saveAll(List.of(
                    new Plato(null, "Lomo saltado", "Carne salteada con cebolla, tomate y papas fritas.", new BigDecimal("18.50")),
                    new Plato(null, "Aji de gallina", "Pollo deshilachado en crema de aji amarillo.", new BigDecimal("16.00")),
                    new Plato(null, "Tallarin verde", "Pasta con salsa de albahaca y bistec apanado.", new BigDecimal("17.50")),
                    new Plato(null, "Ensalada fresca", "Mix de vegetales con aderezo citrico.", new BigDecimal("12.00"))
            ));
        };
    }
}
