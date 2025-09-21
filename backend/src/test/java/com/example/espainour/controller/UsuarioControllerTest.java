package com.example.espainour.controller;

import com.example.espainour.dto.UsuarioDTO;
import com.example.espainour.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void testGetTodosUsuarios() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(1L);
        dto.setNombre("Carlos");
        dto.setTipoUsuario("voluntario");

        when(usuarioService.getTodosUsuarios()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Carlos"))
                .andExpect(jsonPath("$[0].tipoUsuario").value("voluntario"));
    }
}
