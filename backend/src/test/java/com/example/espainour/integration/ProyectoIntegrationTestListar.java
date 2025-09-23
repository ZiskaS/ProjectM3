package com.example.espainour.integration;

import com.example.espainour.model.Proyecto;
import com.example.espainour.repository.ProyectoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProyectoIntegrationTestListar {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProyectoRepository proyectoRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        proyectoRepo.deleteAll();

        Proyecto p1 = new Proyecto("Escuela", "Construcción de escuela", List.of("educación"), LocalDate.now(), LocalDate.now());
        Proyecto p2 = new Proyecto("Hospital", "Renovación de hospital", List.of("salud"), LocalDate.now(), LocalDate.now());
        Proyecto p3 = new Proyecto("Agua", "Suministro de agua potable", List.of("infraestructura"), LocalDate.now(), LocalDate.now());

        proyectoRepo.saveAll(List.of(p1, p2, p3));
    }

    @Test
    void testListarSinSearch() throws Exception {
        mockMvc.perform(get("/api/proyectos")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.meta.page").value(1))
                .andExpect(jsonPath("$.meta.total").value(3));
    }

    @Test
    void testListarConSearchPorTitulo() throws Exception {
        mockMvc.perform(get("/api/proyectos")
                        .param("search", "Escuela")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Escuela"))
                .andExpect(jsonPath("$.meta.total").value(1));
    }

    @Test
    void testListarConSearchPorTag() throws Exception {
        mockMvc.perform(get("/api/proyectos")
                        .param("search", "salud")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("Hospital"))
                .andExpect(jsonPath("$.meta.total").value(1));
    }
}
