package com.example.espainour.service;

import com.example.espainour.model.TipoJornada;
import com.example.espainour.model.Voluntario;
import com.example.espainour.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VoluntarioService {

    private final VoluntarioRepository voluntarioRepository;

    public VoluntarioService(VoluntarioRepository voluntarioRepository) {
        this.voluntarioRepository = voluntarioRepository;
    }

    public List<Voluntario> findAll() {
        return voluntarioRepository.findAll();
    }

    public Optional<Voluntario> findById(Long id) {
        return voluntarioRepository.findById(id);
    }

    public Voluntario save(Voluntario voluntario) {
        // Validación: fechaDisponibilidad no puede ser nula ni en el pasado
        if (voluntario.getFechaDisponibilidad() == null) {
            throw new IllegalArgumentException("La fecha de disponibilidad es obligatoria.");
        }
        if (voluntario.getFechaDisponibilidad().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de disponibilidad no puede estar en el pasado.");
        }

        // Validación de tipoJornada y horarios
        if (voluntario.getTipoJornada() == TipoJornada.FULL_TIME) {
            voluntario.setHorariosDisponibilidad(null); // ignorar horarios si es full-time
        } else if (voluntario.getTipoJornada() == TipoJornada.MEDIO_TIEMPO) {
            if (voluntario.getHorariosDisponibilidad() == null || voluntario.getHorariosDisponibilidad().isEmpty()) {
                throw new IllegalArgumentException("Debes seleccionar al menos un horario para medio tiempo.");
            }
        }

        // Generar voluntarioNumero si es nuevo
        if (voluntario.getId() == null) {
            Long maxNumero = voluntarioRepository.findMaxVoluntarioNumero();
            if (maxNumero == null) {
                maxNumero = 0L;
            }
            voluntario.setVoluntarioNumero(maxNumero + 1);
        }

        return voluntarioRepository.save(voluntario);
    }

    public Voluntario update(Long id, Voluntario voluntarioDetails) {
        return voluntarioRepository.findById(id)
                .map(voluntario -> {
                    voluntario.setNombre(voluntarioDetails.getNombre());
                    voluntario.setApellidos(voluntarioDetails.getApellidos());
                    voluntario.setTelefono(voluntarioDetails.getTelefono());
                    voluntario.setEmail(voluntarioDetails.getEmail());
                    voluntario.setDocumentoIdentidad(voluntarioDetails.getDocumentoIdentidad());
                    voluntario.setFechaDisponibilidad(voluntarioDetails.getFechaDisponibilidad());
                    voluntario.setTipoJornada(voluntarioDetails.getTipoJornada());
                    voluntario.setHorariosDisponibilidad(voluntarioDetails.getHorariosDisponibilidad());
                    voluntario.setAreasInteres(voluntarioDetails.getAreasInteres());
                    voluntario.setHabilidades(voluntarioDetails.getHabilidades());

                    return voluntarioRepository.save(voluntario);
                })
                .orElseThrow(() -> new RuntimeException("Voluntario no encontrado con id " + id));
    }

    public void deleteById(Long id) {
        voluntarioRepository.deleteById(id);
    }
}
