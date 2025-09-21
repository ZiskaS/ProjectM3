package com.example.espainour.controller;

import com.example.espainour.model.Voluntario;
import com.example.espainour.service.VoluntarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoluntarioController.class)
class VoluntarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoluntarioService voluntarioService;

    @Test
    void testGetVoluntarioById() throws Exception {
        Voluntario v = new Voluntario();
        v.setId(1L);
        v.setNombre("Luis");

        when(voluntarioService.findById(1L)).thenReturn(Optional.of(v));

        mockMvc.perform(get("/api/voluntarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Luis"));
    }
}
