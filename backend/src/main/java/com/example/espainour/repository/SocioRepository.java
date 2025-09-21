package com.example.espainour.repository;

import com.example.espainour.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SocioRepository extends JpaRepository<Socio, Long> {
    List<Socio> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT MAX(s.socioNumero) FROM Socio s")
    Long findMaxSocioNumero();

    Optional<Socio> findBySocioNumero(Long socioNumero);
}
