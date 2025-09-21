package com.example.espainour.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String title;

    private String description;

    @ElementCollection
    @CollectionTable(name = "proyecto_tags", joinColumns = @JoinColumn(name = "proyecto_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();

    public Proyecto() {}

    public Proyecto(Long id, String title, String description, List<String> tags, LocalDate createdAt, LocalDate updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = tags != null ? tags : new ArrayList<>();
        this.createdAt = createdAt != null ? createdAt : LocalDate.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDate.now();
    }

    public Proyecto(String title, String description, List<String> tags, LocalDate createdAt, LocalDate updatedAt) {
        this(null, title, description, tags, createdAt, updatedAt);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags != null ? tags : new ArrayList<>(); }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt != null ? createdAt : LocalDate.now(); }

    public LocalDate getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt != null ? updatedAt : LocalDate.now(); }
}
