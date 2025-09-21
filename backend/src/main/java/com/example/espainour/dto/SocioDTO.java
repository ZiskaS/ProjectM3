package com.example.espainour.dto;

import com.example.espainour.model.TipoSocio;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class SocioDTO {

    private Long id;

    private Long socioNumero;

    @NotBlank(message = "Nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Apellidos son obligatorios")
    private String apellidos;

    @Email(message = "Email debe ser válido")
    private String email;

    private String telefono;

    private String documentoIdentidad;

    @NotNull(message = "Cuota mensual es obligatoria")
    @PositiveOrZero(message = "Cuota mensual no puede ser negativa")
    private Double cuotaMensual;

    @NotNull(message = "Tipo de socio es obligatorio")
    private TipoSocio tipoSocio;

    @PastOrPresent(message = "La fecha de pago no puede estar en el futuro")
    private LocalDate fechaPago;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSocioNumero() {
        return socioNumero;
    }

    public void setSocioNumero(Long socioNumero) {
        this.socioNumero = socioNumero;
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

    public Double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(Double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public TipoSocio getTipoSocio() {
        return tipoSocio;
    }

    public void setTipoSocio(TipoSocio tipoSocio) {
        this.tipoSocio = tipoSocio;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }
}
