package com.example.espainour.integration;

import com.example.espainour.dto.ProyectoDTO;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProyectoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProyectoRepository proyectoRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        proyectoRepo.deleteAll();
    }

    @Test
    void testCRUDProyecto() throws Exception {
        ProyectoDTO dto = new ProyectoDTO("Proyecto A", "Descripción A", List.of("tag1", "tag2"));

        String createResponse = mockMvc.perform(post("/api/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Proyecto A"))
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andReturn().getResponse().getContentAsString();

        Proyecto created = objectMapper.readValue(createResponse, Proyecto.class);
        Long id = created.getId();

        assertThat(proyectoRepo.findById(id)).isPresent();

        mockMvc.perform(get("/api/proyectos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Proyecto A"))
                .andExpect(jsonPath("$.tags", hasSize(2)));

        ProyectoDTO updateDto = new ProyectoDTO("Proyecto B", "Descripción B", List.of("tagX"));

        mockMvc.perform(put("/api/proyectos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Proyecto B"))
                .andExpect(jsonPath("$.description").value("Descripción B"))
                .andExpect(jsonPath("$.tags", hasSize(1)))
                .andExpect(jsonPath("$.tags[0]").value("tagX"));

        mockMvc.perform(delete("/api/proyectos/" + id))
                .andExpect(status().isNoContent());

        assertThat(proyectoRepo.findById(id)).isNotPresent();
    }
}
