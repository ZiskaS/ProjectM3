package com.example.espainour.service;

import com.example.espainour.model.Proyecto;
import com.example.espainour.repository.ProyectoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepo;

    public ProyectoService(ProyectoRepository proyectoRepo) {
        this.proyectoRepo = proyectoRepo;
    }

    public Page<Proyecto> listarProyectos(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return proyectoRepo.findAll(pageable);
        }
        return proyectoRepo.findByTitleContainingIgnoreCaseOrTagsIn(search, List.of(search), pageable);
    }

    public Proyecto crearProyecto(Proyecto proyecto) {
        proyecto.setCreatedAt(LocalDate.now());
        proyecto.setUpdatedAt(LocalDate.now());
        proyecto.setTags(proyecto.getTags() != null ? proyecto.getTags() : new ArrayList<>());
        return proyectoRepo.save(proyecto);
    }

    public Optional<Proyecto> obtenerProyecto(Long id) {
        return proyectoRepo.findById(id);
    }

    public Optional<Proyecto> actualizarProyecto(Long id, Proyecto proyecto) {
        return proyectoRepo.findById(id).map(p -> {
            p.setTitle(proyecto.getTitle());
            p.setDescription(proyecto.getDescription());
            p.setTags(proyecto.getTags() != null ? proyecto.getTags() : new ArrayList<>());
            p.setUpdatedAt(LocalDate.now());
            return proyectoRepo.save(p);
        });
    }

    public boolean eliminarProyecto(Long id) {
        if (proyectoRepo.existsById(id)) {
            proyectoRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
