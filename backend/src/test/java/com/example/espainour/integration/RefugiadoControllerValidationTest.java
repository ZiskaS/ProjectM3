package com.example.espainour;

import com.example.espainour.dto.RefugiadoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RefugiadoControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenInvalidRefugiadoData_thenReturnsValidationErrors() throws Exception {
        RefugiadoDTO invalidDto = new RefugiadoDTO();
        invalidDto.setNombre("");
        invalidDto.setApellidos("");
        invalidDto.setEmail("invalid-email");
        invalidDto.setTelefono("");
        invalidDto.setDocumentoIdentidad("");
        invalidDto.setNacionalidad("");
        invalidDto.setIdioma(null);
        invalidDto.setEstatusLegal(null);

        String json = objectMapper.writeValueAsString(invalidDto);

        mockMvc.perform(post("/api/refugiados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombre").value("Nombre es obligatorio"))
                .andExpect(jsonPath("$.apellidos").value("Apellidos son obligatorios"))
                .andExpect(jsonPath("$.email").value("Email debe ser válido"))
                .andExpect(jsonPath("$.telefono").value("Teléfono es obligatorio"))
                .andExpect(jsonPath("$.documentoIdentidad").value("Documento de identidad es obligatorio"))
                .andExpect(jsonPath("$.nacionalidad").value("Nacionalidad es obligatoria"))
                .andExpect(jsonPath("$.idioma").value("Idioma es obligatorio"))
                .andExpect(jsonPath("$.estatusLegal").value("Estatus legal es obligatorio"));
    }
}
