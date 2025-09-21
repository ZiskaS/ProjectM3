package com.example.espainour.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class HealthController {

    private final DataSource dataSource;

    public HealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        try (Connection conn = dataSource.getConnection()) {
            if(conn.isValid(1)) {
                return Map.of("status", "ok", "version", "1.0.0");
            }
        } catch (SQLException e) {
            return Map.of("status", "error", "message", e.getMessage());
        }
        return Map.of("status", "error", "message", "No connection");
    }
}
