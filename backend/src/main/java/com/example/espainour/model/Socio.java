package com.example.espainour.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "socios")
@PrimaryKeyJoinColumn(name = "id")
public class Socio extends Usuario {

    @Column(nullable = false, unique = true)
    private Long socioNumero;

    private Double cuotaMensual;

    private LocalDate fechaPago;

    // Tipo de socio (por ejemplo: mensual, anual, donante especial, etc.)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSocio tipoSocio;

    public Socio() {}

    public Long getSocioNumero() {
        return socioNumero;
    }

    public void setSocioNumero(Long socioNumero) {
        this.socioNumero = socioNumero;
    }

    public Double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(Double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public TipoSocio getTipoSocio() {
        return tipoSocio;
    }

    public void setTipoSocio(TipoSocio tipoSocio) {
        this.tipoSocio = tipoSocio;
    }
}
