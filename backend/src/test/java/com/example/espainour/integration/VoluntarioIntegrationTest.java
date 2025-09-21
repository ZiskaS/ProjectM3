package com.example.espainour;

import com.example.espainour.model.Voluntario;
import com.example.espainour.repository.VoluntarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class VoluntarioIntegrationTest {

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Test
    void testFindAllVoluntarios() {
        List<Voluntario> voluntarios = voluntarioRepository.findAll();

        assertThat(voluntarios).isNotEmpty();
        assertThat(voluntarios.size()).isGreaterThanOrEqualTo(2);

        boolean existsMarta = voluntarios.stream()
                .anyMatch(v -> "Marta".equals(v.getNombre()) && "López".equals(v.getApellidos()));
        assertThat(existsMarta).isTrue();
    }
}

