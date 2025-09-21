package com.example.espainour.controller;

import com.example.espainour.model.*;
import com.example.espainour.service.VoluntarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/voluntarios")
public class VoluntarioController {

    private final VoluntarioService voluntarioService;

    public VoluntarioController(VoluntarioService voluntarioService) {
        this.voluntarioService = voluntarioService;
    }

    // GET /api/voluntarios - Obtener todos los voluntarios
    @GetMapping
    public List<Voluntario> getAllVoluntarios() {
        return voluntarioService.findAll();
    }

    // GET /api/voluntarios/{id} - Obtener voluntario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Voluntario> getVoluntarioById(@PathVariable Long id) {
        return voluntarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/voluntarios - Crear nuevo voluntario
    @PostMapping
    public Voluntario createVoluntario(@RequestBody Voluntario voluntario) {
        return voluntarioService.save(voluntario);
    }

    // PUT /api/voluntarios/{id} - Actualizar voluntario completo
    @PutMapping("/{id}")
    public ResponseEntity<Voluntario> updateVoluntario(@PathVariable Long id, @RequestBody Voluntario voluntarioDetails) {
        Optional<Voluntario> optionalExisting = voluntarioService.findById(id);
        if (optionalExisting.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Voluntario existing = optionalExisting.get();

        // No cambiar número único de voluntario
        if (!existing.getVoluntarioNumero().equals(voluntarioDetails.getVoluntarioNumero())) {
            return ResponseEntity.badRequest().build();
        }

        // Actualizar campos permitidos
        existing.setNombre(voluntarioDetails.getNombre());
        existing.setEmail(voluntarioDetails.getEmail());
        existing.setTelefono(voluntarioDetails.getTelefono());
        existing.setFechaDisponibilidad(voluntarioDetails.getFechaDisponibilidad());
        existing.setTipoJornada(voluntarioDetails.getTipoJornada());
        existing.setHabilidades(voluntarioDetails.getHabilidades());
        existing.setAreasInteres(voluntarioDetails.getAreasInteres());
        existing.setHorariosDisponibilidad(voluntarioDetails.getHorariosDisponibilidad());

        Voluntario updated = voluntarioService.save(existing);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/voluntarios/{id} - Eliminar voluntario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoluntario(@PathVariable Long id) {
        voluntarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /api/voluntarios/{id} - Actualizar parcialmente voluntario
    @PatchMapping("/{id}")
    public ResponseEntity<Voluntario> patchVoluntario(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return voluntarioService.findById(id).map(voluntario -> {
            if (updates.containsKey("fechaDisponibilidad")) {
                voluntario.setFechaDisponibilidad(LocalDate.parse((String) updates.get("fechaDisponibilidad")));
            }
            if (updates.containsKey("tipoJornada")) {
                TipoJornada tipo = TipoJornada.valueOf((String) updates.get("tipoJornada"));
                voluntario.setTipoJornada(tipo);

                if (tipo == TipoJornada.FULL_TIME) {
                    voluntario.setHorariosDisponibilidad(null);
                }
            }
            if (updates.containsKey("habilidades")) {
                voluntario.setHabilidades((String) updates.get("habilidades"));
            }
            if (updates.containsKey("areasInteres")) {
                voluntario.setAreasInteres(AreaInteres.valueOf((String) updates.get("areasInteres")));
            }
            if (updates.containsKey("horariosDisponibilidad")) {
                List<String> horarios = (List<String>) updates.get("horariosDisponibilidad");
                Set<HorarioDisponibilidad> horarioSet = new HashSet<>();
                for (String h : horarios) {
                    horarioSet.add(HorarioDisponibilidad.valueOf(h));
                }
                voluntario.setHorariosDisponibilidad(horarioSet);
            }

            Voluntario actualizado = voluntarioService.save(voluntario);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }
}
