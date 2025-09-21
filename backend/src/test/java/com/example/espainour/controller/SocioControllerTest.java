package com.example.espainour.controller;

import com.example.espainour.model.Socio;
import com.example.espainour.service.SocioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SocioController.class)
class SocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SocioService socioService;

    @Test
    void testGetSocioById() throws Exception {
        Socio s = new Socio();
        s.setId(1L);
        s.setNombre("Marta");

        when(socioService.findById(1L)).thenReturn(Optional.of(s));

        mockMvc.perform(get("/api/socios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Marta"));
    }
}

