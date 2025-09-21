package com.example.espainour.dto;

import com.example.espainour.model.Genero; // Asegúrate de importar el enum

public class UsuarioDTO {

    private Long id;
    private String tipoUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private Genero genero; // ← NUEVO CAMPO

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String tipoUsuario, String nombre, String apellidos, String email, String telefono, Genero genero) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.genero = genero;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
