package com.example.espainour.model;

import jakarta.persistence.*;

@Entity
@Table(name = "refugiados")
@PrimaryKeyJoinColumn(name = "id")
public class Refugiado extends Usuario {

    @Column(nullable = false, unique = true)
    private Long refugiadoNumero;

    @Column
    private String nacionalidad;

    @Column
    private String idioma;

    // Estatus legal del refugiado (por ejemplo: solicitud en trámite, aprobado, denegado)
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus_legal", nullable = false)
    private EstatusLegal estatusLegal;

    // --- Getter & Setter ---
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

    public Refugiado() {}
}
