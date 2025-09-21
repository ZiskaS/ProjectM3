package com.example.espainour.repository;

import com.example.espainour.model.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    Page<Proyecto> findByTitleContainingIgnoreCaseOrTagsIn(String title, List<String> tags, Pageable pageable);
}
