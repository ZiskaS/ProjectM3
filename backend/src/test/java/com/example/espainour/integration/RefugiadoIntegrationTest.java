package com.example.espainour;

import com.example.espainour.model.Refugiado;
import com.example.espainour.repository.RefugiadoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RefugiadoIntegrationTest {

    @Autowired
    private RefugiadoRepository refugiadoRepository;

    @Test
    void testFindAllRefugiados() {
        List<Refugiado> refugiados = refugiadoRepository.findAll();

        assertThat(refugiados).isNotEmpty();
        assertThat(refugiados.size()).isGreaterThanOrEqualTo(2);

        boolean existsFatima = refugiados.stream()
                .anyMatch(r -> "Fatima".equals(r.getNombre()) && "Khalil".equals(r.getApellidos()));
        assertThat(existsFatima).isTrue();
    }
}
