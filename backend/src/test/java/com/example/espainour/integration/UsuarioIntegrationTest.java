package com.example.espainour.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetTodosUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(hasSize(6))) // 2 Socios + 2 Voluntarios + 2 Refugiados aus DataLoader
                .andExpect(jsonPath("$[?(@.nombre == 'Laura')]").exists())
                .andExpect(jsonPath("$[?(@.nombre == 'Fatima')]").exists())
                .andExpect(jsonPath("$[?(@.nombre == 'Marta')]").exists());
    }
}
