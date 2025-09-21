package com.example.espainour;

import com.example.espainour.model.Socio;
import com.example.espainour.repository.SocioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SocioIntegrationTest {

    @Autowired
    private SocioRepository socioRepository;

    @Test
    void testFindAllSocios() {
        List<Socio> socios = socioRepository.findAll();

        assertThat(socios).isNotEmpty();
        assertThat(socios.size()).isGreaterThanOrEqualTo(2);

        boolean existsLaura = socios.stream()
                .anyMatch(s -> "Laura".equals(s.getNombre()) && "Gómez".equals(s.getApellidos()));
        assertThat(existsLaura).isTrue();
    }
}
