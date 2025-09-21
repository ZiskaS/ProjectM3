package com.example.espainour.service;

import com.example.espainour.model.Socio;
import com.example.espainour.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    @Autowired
    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public List<Socio> findAll() {
        return socioRepository.findAll();
    }

    public Optional<Socio> findById(Long id) {
        return socioRepository.findById(id);
    }

    @Transactional
    public Socio crearSocio(Socio socio) {
        if (socio.getId() == null) {
            Long maxNumero = socioRepository.findMaxSocioNumero();
            if (maxNumero == null) {
                maxNumero = 0L;
            }
            socio.setSocioNumero(maxNumero + 1);
        }
        return socioRepository.save(socio);
    }

    public void deleteById(Long id) {
        socioRepository.deleteById(id);
    }

    public Optional<Socio> findBySocioNumero(Long socioNumero) {
        return socioRepository.findBySocioNumero(socioNumero);
    }

}