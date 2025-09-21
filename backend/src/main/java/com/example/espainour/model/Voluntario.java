package com.example.espainour.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "voluntarios")
@PrimaryKeyJoinColumn(name = "id")
public class Voluntario extends Usuario {

    @Column(nullable = false, unique = true)
    private Long voluntarioNumero;

    @Column(nullable = false)
    private LocalDate fechaDisponibilidad;

    @Enumerated(EnumType.STRING)
    private TipoJornada tipoJornada;

    // Lista de horarios disponibles para el voluntario (valores tipo Enum)
    @ElementCollection(targetClass = HorarioDisponibilidad.class)
    @CollectionTable(name = "voluntario_horarios", joinColumns = @JoinColumn(name = "voluntario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "horario")
    private Set<HorarioDisponibilidad> horariosDisponibilidad;

    private String habilidades;

    @Enumerated(EnumType.STRING)
    private AreaInteres areasInteres;


    public Long getVoluntarioNumero() {
        return voluntarioNumero;
    }

    public void setVoluntarioNumero(Long voluntarioNumero) {
        this.voluntarioNumero = voluntarioNumero;
    }

    public LocalDate getFechaDisponibilidad() {
        return fechaDisponibilidad;
    }

    public void setFechaDisponibilidad(LocalDate fechaDisponibilidad) {
        this.fechaDisponibilidad = fechaDisponibilidad;
    }

    public TipoJornada getTipoJornada() {
        return tipoJornada;
    }

    public void setTipoJornada(TipoJornada tipoJornada) {
        this.tipoJornada = tipoJornada;
    }

    public Set<HorarioDisponibilidad> getHorariosDisponibilidad() {
        return horariosDisponibilidad;
    }

    public void setHorariosDisponibilidad(Set<HorarioDisponibilidad> horariosDisponibilidad) {
        this.horariosDisponibilidad = horariosDisponibilidad;
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

    public Voluntario() {}
}
