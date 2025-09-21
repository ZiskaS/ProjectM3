package com.example.espainour.service;

import com.example.espainour.dto.UsuarioDTO;
import com.example.espainour.model.Refugiado;
import com.example.espainour.model.Socio;
import com.example.espainour.model.Voluntario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final SocioService socioService;
    private final VoluntarioService voluntarioService;
    private final RefugiadoService refugiadoService;

    public UsuarioService(SocioService socioService, VoluntarioService voluntarioService, RefugiadoService refugiadoService) {
        this.socioService = socioService;
        this.voluntarioService = voluntarioService;
        this.refugiadoService = refugiadoService;
    }

    public List<UsuarioDTO> getTodosUsuarios() {
        List<UsuarioDTO> todos = new ArrayList<>();
        List<Socio> socios = socioService.findAll();
        socios.forEach(s -> todos.add(socioToUsuarioDTO(s)));
        List<Voluntario> voluntarios = voluntarioService.findAll();
        voluntarios.forEach(v -> todos.add(voluntarioToUsuarioDTO(v)));
        List<Refugiado> refugiados = refugiadoService.findAll();
        refugiados.forEach(r -> todos.add(refugiadoToUsuarioDTO(r)));
        return todos;
    }

    public boolean deleteById(Long id) {
        if (socioService.findById(id).isPresent()) {
            socioService.deleteById(id);
            return true;
        }
        if (voluntarioService.findById(id).isPresent()) {
            voluntarioService.deleteById(id);
            return true;
        }
        if (refugiadoService.findById(id).isPresent()) {
            refugiadoService.deleteById(id);
            return true;
        }
        return false;
    }

    private UsuarioDTO socioToUsuarioDTO(Socio socio) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(socio.getId());
        dto.setNombre(socio.getNombre());
        dto.setApellidos(socio.getApellidos());
        dto.setEmail(socio.getEmail());
        dto.setTelefono(socio.getTelefono());
        dto.setTipoUsuario("socio");
        dto.setGenero(socio.getGenero());
        return dto;
    }

    private UsuarioDTO voluntarioToUsuarioDTO(Voluntario voluntario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(voluntario.getId());
        dto.setNombre(voluntario.getNombre());
        dto.setApellidos(voluntario.getApellidos());
        dto.setEmail(voluntario.getEmail());
        dto.setTelefono(voluntario.getTelefono());
        dto.setTipoUsuario("voluntario");
        dto.setGenero(voluntario.getGenero());
        return dto;
    }

    private UsuarioDTO refugiadoToUsuarioDTO(Refugiado refugiado) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(refugiado.getId());
        dto.setNombre(refugiado.getNombre());
        dto.setApellidos(refugiado.getApellidos());
        dto.setEmail(refugiado.getEmail());
        dto.setTelefono(refugiado.getTelefono());
        dto.setTipoUsuario("refugiado");
        dto.setGenero(refugiado.getGenero());
        return dto;
    }
}
