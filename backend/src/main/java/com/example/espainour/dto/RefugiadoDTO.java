package com.example.espainour.dto;

import com.example.espainour.model.EstatusLegal;
import com.example.espainour.model.Genero;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RefugiadoDTO {

    private Long id;
    private Long refugiadoNumero;

    @NotBlank(message = "Nacionalidad es obligatoria")
    private String nacionalidad;

    @NotBlank(message = "Idioma es obligatorio")
    private String idioma;

    @NotNull(message = "Estatus legal es obligatorio")
    private EstatusLegal estatusLegal;

    @NotNull(message = "Género es obligatorio")
    private Genero genero;

    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Apellidos son obligatorios")
    private String apellidos;

    @Email(message = "Email debe ser válido")
    @NotBlank(message = "Email es obligatorio")
    private String email;

    @NotBlank(message = "Teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "Documento de identidad es obligatorio")
    private String documentoIdentidad;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime fechaRegistro;

    public RefugiadoDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefugiadoNumero() {
        return refugiadoNumero;
    }

    public void setRefugiadoNumero(Long refugiadoNumero) {
        this.refugiadoNumero = refugiadoNumero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public EstatusLegal getEstatusLegal() {
        return estatusLegal;
    }

    public void setEstatusLegal(EstatusLegal estatusLegal) {
        this.estatusLegal = estatusLegal;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
