package com.example.espainour.integration;

import com.example.espainour.model.Proyecto;
import com.example.espainour.repository.ProyectoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @BeforeEach
    void setup() {
        proyectoRepo.deleteAll();

        Proyecto p1 = new Proyecto("Escuela", "Construcción de escuela", List.of("educación"));
        Proyecto p2 = new Proyecto("Hospital", "Renovación de hospital", List.of("salud"));
        Proyecto p3 = new Proyecto("Agua", "Suministro de agua potable", List.of("infraestructura"));
        Proyecto p4 = new Proyecto("Escuela Secundaria", "Construcción de secundaria", List.of("educación"));
        Proyecto p5 = new Proyecto("Clínica", "Ampliación de clínica", List.of("salud"));

        proyectoRepo.saveAll(List.of(p1, p2, p3, p4, p5));
    }

    @Test
    void testListarSinSearch() throws Exception {
        mockMvc.perform(get("/api/proyectos")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(5)))
                .andExpect(jsonPath("$.meta.page").value(1))
                .andExpect(jsonPath("$.meta.total").value(5));
    }

    @Test
    void testListarConSearchPorTitulo() throws Exception {
        mockMvc.perform(get("/api/proyectos")
                        .param("search", "Escuela")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].title", containsString("Escuela")))
                .andExpect(jsonPath("$.meta.total").value(2));
    }

    @Test
    void testListarConSearchPorTag() throws Exception {
        mockMvc.perform(get("/api/proyectos")
                        .param("search", "salud")
                        .param("page", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[*].tags", everyItem(hasItem("salud"))))
                .andExpect(jsonPath("$.meta.total").value(2));
    }

    @Test
    void testPagination() throws Exception {
        mockMvc.perform(get("/api/proyectos")
                        .param("page", "1")
                        .param("pageSize", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.meta.page").value(1))
                .andExpect(jsonPath("$.meta.pageSize").value(2))
                .andExpect(jsonPath("$.meta.total").value(5));

        mockMvc.perform(get("/api/proyectos")
                        .param("page", "3")
                        .param("pageSize", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.meta.page").value(3))
                .andExpect(jsonPath("$.meta.pageSize").value(2))
                .andExpect(jsonPath("$.meta.total").value(5));
    }
}
