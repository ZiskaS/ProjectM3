package com.example.espainour.controller;

import com.example.espainour.model.Socio;
import com.example.espainour.model.TipoSocio;
import com.example.espainour.service.SocioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    // GET /api/socios - Devuelve todos los socios
    @GetMapping
    public List<Socio> getAllSocios() {
        return socioService.findAll();
    }

    // GET /api/socios/{id} - Devuelve un socio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Socio> getSocioById(@PathVariable Long id) {
        return socioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/socios - Crea un nuevo socio
    @PostMapping
    public Socio createSocio(@RequestBody Socio socio) {
        return socioService.crearSocio(socio);
    }

    // PUT /api/socios/{id} - Actualiza todos los campos permitidos del socio
    @PutMapping("/{id}")
    public ResponseEntity<Socio> updateSocio(@PathVariable Long id, @RequestBody Socio socioDetails) {
        Optional<Socio> optionalExisting = socioService.findById(id);

        if (optionalExisting.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Socio existing = optionalExisting.get();

        // El número de socio no se puede modificar
        if (!existing.getSocioNumero().equals(socioDetails.getSocioNumero())) {
            return ResponseEntity.badRequest().build();
        }

        existing.setNombre(socioDetails.getNombre());
        existing.setApellidos(socioDetails.getApellidos());
        existing.setEmail(socioDetails.getEmail());
        existing.setTelefono(socioDetails.getTelefono());
        existing.setDocumentoIdentidad(socioDetails.getDocumentoIdentidad());
        existing.setCuotaMensual(socioDetails.getCuotaMensual());
        existing.setTipoSocio(socioDetails.getTipoSocio());
        existing.setFechaPago(socioDetails.getFechaPago());

        Socio updated = socioService.crearSocio(existing);
        return ResponseEntity.ok(updated);
    }

    // PATCH /api/socios/{id} - Modificación parcial de campos permitidos
    @PatchMapping("/{id}")
    public ResponseEntity<Socio> patchSocio(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return socioService.findById(id).map(existing -> {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "nombre":
                        existing.setNombre((String) value);
                        break;
                    case "apellidos":
                        existing.setApellidos((String) value);
                        break;
                    case "email":
                        existing.setEmail((String) value);
                        break;
                    case "telefono":
                        existing.setTelefono((String) value);
                        break;
                    case "documentoIdentidad":
                        existing.setDocumentoIdentidad((String) value);
                        break;
                    case "cuotaMensual":
                        existing.setCuotaMensual(Double.valueOf(value.toString()));
                        break;
                    case "tipoSocio":
                        existing.setTipoSocio(TipoSocio.valueOf(value.toString()));
                        break;
                    case "fechaPago":
                        existing.setFechaPago(LocalDate.parse(value.toString()));
                        break;
                    // socioNumero y genero no se deben modificar mediante PATCH
                }
            });
            Socio updated = socioService.crearSocio(existing);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/socios/{id} - Elimina un socio por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocio(@PathVariable Long id) {
        socioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
