package com.example.espainour.controller;

import com.example.espainour.dto.RefugiadoDTO;
import com.example.espainour.model.Refugiado;
import com.example.espainour.model.EstatusLegal;
import com.example.espainour.service.RefugiadoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/refugiados")
public class RefugiadoController {

    private final RefugiadoService refugiadoService;

    public RefugiadoController(RefugiadoService refugiadoService) {
        this.refugiadoService = refugiadoService;
    }

    // Convierte una entidad Refugiado en su DTO correspondiente
    private RefugiadoDTO toDTO(Refugiado r) {
        RefugiadoDTO dto = new RefugiadoDTO();
        dto.setId(r.getId());
        dto.setRefugiadoNumero(r.getRefugiadoNumero());
        dto.setNombre(r.getNombre());
        dto.setApellidos(r.getApellidos());
        dto.setEmail(r.getEmail());
        dto.setTelefono(r.getTelefono());
        dto.setDocumentoIdentidad(r.getDocumentoIdentidad());
        dto.setNacionalidad(r.getNacionalidad());
        dto.setIdioma(r.getIdioma());
        dto.setEstatusLegal(r.getEstatusLegal());
        dto.setGenero(r.getGenero());
        dto.setFechaRegistro(r.getFechaRegistro());
        return dto;
    }

    // Convierte un DTO en una entidad Refugiado para persistencia
    private Refugiado toEntity(RefugiadoDTO dto) {
        Refugiado r = new Refugiado();
        r.setId(dto.getId());
        r.setRefugiadoNumero(dto.getRefugiadoNumero());
        r.setNombre(dto.getNombre());
        r.setApellidos(dto.getApellidos());
        r.setEmail(dto.getEmail());
        r.setTelefono(dto.getTelefono());
        r.setDocumentoIdentidad(dto.getDocumentoIdentidad());
        r.setNacionalidad(dto.getNacionalidad());
        r.setIdioma(dto.getIdioma());
        r.setEstatusLegal(dto.getEstatusLegal());
        r.setGenero(dto.getGenero());
        return r;
    }

    // GET /api/refugiados - Obtiene todos los refugiados
    @GetMapping
    public List<RefugiadoDTO> getAllRefugiados() {
        List<Refugiado> refugiados = refugiadoService.findAll();
        return refugiados.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // GET /api/refugiados/{id} - Devuelve un refugiado por ID
    @GetMapping("/{id}")
    public ResponseEntity<RefugiadoDTO> getRefugiadoById(@PathVariable Long id) {
        Optional<Refugiado> opt = refugiadoService.findById(id);
        return opt.map(ref -> ResponseEntity.ok(toDTO(ref)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/refugiados - Crea un nuevo refugiado
    @PostMapping
    public ResponseEntity<RefugiadoDTO> createRefugiado(@Valid @RequestBody RefugiadoDTO refugiadoDTO) {
        Refugiado refugiado = toEntity(refugiadoDTO);
        Refugiado saved = refugiadoService.crearRefugiado(refugiado);
        return ResponseEntity.ok(toDTO(saved));
    }

    // PUT /api/refugiados/{id} - Actualiza un refugiado completo
    @PutMapping("/{id}")
    public ResponseEntity<RefugiadoDTO> updateRefugiado(@PathVariable Long id, @Valid @RequestBody RefugiadoDTO refugiadoDTO) {
        Optional<Refugiado> optionalExisting = refugiadoService.findById(id);
        if (optionalExisting.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Refugiado existing = optionalExisting.get();

        // No se permite cambiar el número de refugiado
        if (refugiadoDTO.getRefugiadoNumero() != null &&
                !existing.getRefugiadoNumero().equals(refugiadoDTO.getRefugiadoNumero())) {
            return ResponseEntity.badRequest().build();
        }

        existing.setNombre(refugiadoDTO.getNombre());
        existing.setApellidos(refugiadoDTO.getApellidos());
        existing.setEmail(refugiadoDTO.getEmail());
        existing.setTelefono(refugiadoDTO.getTelefono());
        existing.setDocumentoIdentidad(refugiadoDTO.getDocumentoIdentidad());
        existing.setNacionalidad(refugiadoDTO.getNacionalidad());
        existing.setIdioma(refugiadoDTO.getIdioma());
        existing.setEstatusLegal(refugiadoDTO.getEstatusLegal());

        Refugiado updated = refugiadoService.crearRefugiado(existing);
        return ResponseEntity.ok(toDTO(updated));
    }

    // DELETE /api/refugiados/{id} - Elimina un refugiado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRefugiado(@PathVariable Long id) {
        refugiadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PATCH /api/refugiados/{id} - Actualiza parcialmente un refugiado
    @PatchMapping("/{id}")
    public ResponseEntity<RefugiadoDTO> patchRefugiado(@PathVariable Long id, @RequestBody java.util.Map<String, Object> updates) {
        Optional<Refugiado> optionalExisting = refugiadoService.findById(id);
        if (optionalExisting.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Refugiado existing = optionalExisting.get();

        // Aplica solo los campos que vienen en la petición
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
                case "nacionalidad":
                    existing.setNacionalidad((String) value);
                    break;
                case "idioma":
                    existing.setIdioma((String) value);
                    break;
                case "estatusLegal":
                    existing.setEstatusLegal(EstatusLegal.valueOf((String) value));
                    break;
            }
        });

        Refugiado updated = refugiadoService.crearRefugiado(existing);
        return ResponseEntity.ok(toDTO(updated));
    }
}

