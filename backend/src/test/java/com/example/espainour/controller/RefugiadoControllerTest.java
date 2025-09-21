package com.example.espainour.controller;

import com.example.espainour.dto.RefugiadoDTO;
import com.example.espainour.service.RefugiadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RefugiadoController.class)
class RefugiadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RefugiadoService refugiadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateRefugiadoValidationError() throws Exception {
        // Create DTO with missing required fields to trigger validation errors
        RefugiadoDTO dto = new RefugiadoDTO();
        dto.setNombre("");  // empty name triggers @NotBlank
        dto.setEmail("invalid-email"); // invalid email format
        dto.setNacionalidad(null); // null triggers @NotBlank / @NotNull
        // other required fields also missing

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/refugiados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").value("Nombre es obligatorio"))
                .andExpect(jsonPath("$.email").value("Email debe ser válido"))
                .andExpect(jsonPath("$.nacionalidad").value("Nacionalidad es obligatoria"));
    }
}

