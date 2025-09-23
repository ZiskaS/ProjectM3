package com.example.espainour.controller;

import com.example.espainour.model.Proyecto;
import com.example.espainour.dto.ProyectoDTO;
import com.example.espainour.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public Map<String, Object> listar(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        var proyectos = proyectoService.listarProyectos(search, pageable);

        return Map.of(
                "data", proyectos.getContent(),
                "meta", Map.of(
                        "page", proyectos.getNumber() + 1,
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
        proyecto.setTags(dto.getTags() != null ? dto.getTags() : new ArrayList<>());
        Proyecto saved = proyectoService.crearProyecto(proyecto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtener(@PathVariable Long id) {
        return proyectoService.obtenerProyecto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizar(@PathVariable Long id, @Valid @RequestBody ProyectoDTO dto) {
        Proyecto proyecto = new Proyecto();
        proyecto.setTitle(dto.getTitle());
        proyecto.setDescription(dto.getDescription());
        proyecto.setTags(dto.getTags() != null ? dto.getTags() : new ArrayList<>());

        return proyectoService.actualizarProyecto(id, proyecto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (proyectoService.eliminarProyecto(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

