package com.example.espainour.service;

import com.example.espainour.model.Refugiado;
import com.example.espainour.repository.RefugiadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefugiadoService {

    private final RefugiadoRepository refugiadoRepository;

    @Autowired
    public RefugiadoService(RefugiadoRepository refugiadoRepository) {
        this.refugiadoRepository = refugiadoRepository;
    }

    public List<Refugiado> findAll() {
        return refugiadoRepository.findAll();
    }

    public Optional<Refugiado> findById(Long id) {
        return refugiadoRepository.findById(id);
    }

    @Transactional
    public Refugiado crearRefugiado(Refugiado refugiado) {
        if (refugiado.getRefugiadoNumero() == null) {
            Long maxNumero = refugiadoRepository.findMaxRefugiadoNumero();
            if (maxNumero == null) {
                maxNumero = 0L;
            }
            refugiado.setRefugiadoNumero(maxNumero + 1);
        }
        return refugiadoRepository.save(refugiado);
    }

    public void deleteById(Long id) {
        refugiadoRepository.deleteById(id);
    }

    public Optional<Refugiado> findByRefugiadoNumero(Long refugiadoNumero) {
        return refugiadoRepository.findByRefugiadoNumero(refugiadoNumero);
    }
}
