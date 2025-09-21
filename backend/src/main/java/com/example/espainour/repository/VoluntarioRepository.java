package com.example.espainour.repository;

import com.example.espainour.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    List<Voluntario> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT MAX(v.voluntarioNumero) FROM Voluntario v")
    Long findMaxVoluntarioNumero();
}
