package com.example.espainour.dto;

import com.example.espainour.model.AreaInteres;
import com.example.espainour.model.HorarioDisponibilidad;
import com.example.espainour.model.TipoJornada;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class VoluntarioDTO {

    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "Email es obligatorio")
    @Email(message = "Email no es válido")
    private String email;

    @NotBlank(message = "Teléfono es obligatorio")
    @Pattern(regexp = "\\+?[0-9\\- ]{7,15}", message = "Teléfono no válido")
    private String telefono;

    @NotBlank(message = "Documento de identidad es obligatorio")
    private String documentoIdentidad;

    @NotNull(message = "Fecha de disponibilidad es obligatoria")
    @FutureOrPresent(message = "La fecha de disponibilidad no puede estar en el pasado")
    private LocalDate fechaDisponibilidad;

    @NotNull(message = "Horario de disponibilidad es obligatorio")
    private HorarioDisponibilidad horarioDisponibilidad;

    @NotNull(message = "Tipo de jornada es obligatorio")
    private TipoJornada tipoJornada;

    private String habilidades;

    @NotNull(message = "Área de interés es obligatorio")
    private AreaInteres areasInteres;

    // Getters y Setters

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

    public LocalDate getFechaDisponibilidad() {
        return fechaDisponibilidad;
    }

    public void setFechaDisponibilidad(LocalDate fechaDisponibilidad) {
        this.fechaDisponibilidad = fechaDisponibilidad;
    }

    public HorarioDisponibilidad getHorarioDisponibilidad() {
        return horarioDisponibilidad;
    }

    public void setHorarioDisponibilidad(HorarioDisponibilidad horarioDisponibilidad) {
        this.horarioDisponibilidad = horarioDisponibilidad;
    }

    public TipoJornada getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(TipoJornada tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public AreaInteres getAreasInteres() {
        return areasInteres;
    }

    public void setAreasInteres(AreaInteres areasInteres) {
        this.areasInteres = areasInteres;
    }
}
