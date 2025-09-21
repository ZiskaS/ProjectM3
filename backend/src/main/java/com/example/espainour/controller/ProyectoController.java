package com.example.espainour.controller;

import com.example.espainour.model.Proyecto;
import com.example.espainour.repository.ProyectoRepository;
import com.example.espainour.dto.ProyectoDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoRepository proyectoRepo;

    public ProyectoController(ProyectoRepository proyectoRepo) {
        this.proyectoRepo = proyectoRepo;
    }

    @GetMapping
    public Map<String, Object> listar(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Proyecto> proyectos;

        if (search == null || search.isEmpty()) {
            proyectos = proyectoRepo.findAll(pageable);
        } else {
            proyectos = proyectoRepo.findByTitleContainingIgnoreCaseOrTagsIn(search, List.of(search), pageable);
        }

        return Map.of(
                "data", proyectos.getContent(),
                "meta", Map.of(
                        "page", proyectos.getNumber(),
                        "pageSize", proyectos.getSize(),
                        "total", proyectos.getTotalElements()
                )
        );
    }

    @PostMapping
    public ResponseEntity<Proyecto> crear(@Valid @RequestBody ProyectoDTO dto) {
        Proyecto proyecto = new Proyecto();
        proyecto.setTitle(dto.getTitle());
        proyecto.setDescription(dto.getDescription());
        proyecto.setTags(dto.getTags());
        proyecto.setCreatedAt(LocalDate.now());
        proyecto.setUpdatedAt(LocalDate.now());
        Proyecto saved = proyectoRepo.save(proyecto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtener(@PathVariable Long id) {
        return proyectoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(@PathVariable Long id, @Valid @RequestBody ProyectoDTO dto) {
        return proyectoRepo.findById(id)
                .map(p -> {
                    p.setTitle(dto.getTitle());
                    p.setDescription(dto.getDescription());
                    p.setTags(dto.getTags());
                    p.setUpdatedAt(LocalDate.now());
                    return ResponseEntity.ok(proyectoRepo.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (proyectoRepo.existsById(id)) {
            proyectoRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
