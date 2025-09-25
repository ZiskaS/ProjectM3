package com.example.espainour.config;

import com.example.espainour.model.*;
import com.example.espainour.repository.ProyectoRepository;
import com.example.espainour.repository.RefugiadoRepository;
import com.example.espainour.repository.SocioRepository;
import com.example.espainour.repository.VoluntarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(SocioRepository socioRepo,
                               RefugiadoRepository refugiadoRepo,
                               VoluntarioRepository voluntarioRepo,
                               ProyectoRepository proyectoRepo) {
        return args -> {

            // --- Socios ---
            Socio socio1 = new Socio();
            socio1.setNombre("Laura");
            socio1.setApellidos("Gómez");
            socio1.setEmail("laura.gomez@mail.com");
            socio1.setTelefono("678901234");
            socio1.setDocumentoIdentidad("LM901234Z");
            socio1.setSocioNumero(1L);
            socio1.setCuotaMensual(25.0);
            socio1.setFechaPago(LocalDate.of(2024, 4, 10));
            socio1.setTipoSocio(TipoSocio.DONANTE);
            socio1.setGenero(Genero.FEMENINO);

            Socio socio2 = new Socio();
            socio2.setNombre("Carlos");
            socio2.setApellidos("Pérez");
            socio2.setEmail("carlos.perez@mail.com");
            socio2.setTelefono("612345678");
            socio2.setDocumentoIdentidad("CP567890Y");
            socio2.setSocioNumero(2L);
            socio2.setCuotaMensual(15.0);
            socio2.setFechaPago(LocalDate.of(2024, 6, 5));
            socio2.setTipoSocio(TipoSocio.GENERAL);
            socio2.setGenero(Genero.MASCULINO);

            socioRepo.saveAll(List.of(socio1, socio2));

            // --- Refugiados ---
            Refugiado ref1 = new Refugiado();
            ref1.setNombre("Fatima");
            ref1.setApellidos("Khalil");
            ref1.setEmail("fatima.khalil@mail.com");
            ref1.setTelefono("611998877");
            ref1.setDocumentoIdentidad("FK112233P");
            ref1.setRefugiadoNumero(1L);
            ref1.setNacionalidad("Palestina");
            ref1.setIdioma("Árabe");
            ref1.setEstatusLegal(EstatusLegal.SOLICITANTE_DE_ASILO);
            ref1.setGenero(Genero.FEMENINO);

            Refugiado ref2 = new Refugiado();
            ref2.setNombre("Omar");
            ref2.setApellidos("Nasser");
            ref2.setEmail("omar.nasser@mail.com");
            ref2.setTelefono("611776655");
            ref2.setDocumentoIdentidad("ON334455Q");
            ref2.setRefugiadoNumero(2L);
            ref2.setNacionalidad("Palestina");
            ref2.setIdioma("Árabe");
            ref2.setEstatusLegal(EstatusLegal.REFUGIADO_RECONOCIDO);
            ref2.setGenero(Genero.MASCULINO);

            refugiadoRepo.saveAll(List.of(ref1, ref2));

            // --- Voluntarios ---
            Voluntario vol1 = new Voluntario();
            vol1.setNombre("Marta");
            vol1.setApellidos("López");
            vol1.setEmail("marta.lopez@mail.com");
            vol1.setTelefono("677889900");
            vol1.setDocumentoIdentidad("ML123456Q");
            vol1.setVoluntarioNumero(1L);
            vol1.setFechaDisponibilidad(LocalDate.of(2024, 9, 1));
            vol1.setTipoJornada(TipoJornada.MEDIO_TIEMPO);
            vol1.setHorariosDisponibilidad(Set.of(HorarioDisponibilidad.TARDES, HorarioDisponibilidad.FINES_DE_SEMANA));
            vol1.setHabilidades("Traducción, Gestión de eventos");
            vol1.setAreasInteres(AreaInteres.EVENTOS);
            vol1.setGenero(Genero.FEMENINO);

            Voluntario vol2 = new Voluntario();
            vol2.setNombre("David");
            vol2.setApellidos("Martínez");
            vol2.setEmail("david.martinez@mail.com");
            vol2.setTelefono("699443322");
            vol2.setDocumentoIdentidad("DM445566E");
            vol2.setVoluntarioNumero(2L);
            vol2.setFechaDisponibilidad(LocalDate.of(2024, 10, 1));
            vol2.setTipoJornada(TipoJornada.FULL_TIME);
            vol2.setHabilidades("Educación, Apoyo Legal");
            vol2.setAreasInteres(AreaInteres.LEGALSUPPORT);
            vol2.setGenero(Genero.MASCULINO);

            voluntarioRepo.saveAll(List.of(vol1, vol2));

            // --- Proyectos ---
            if (proyectoRepo.count() == 0) {
                List<Proyecto> proyectos = List.of(
                        new Proyecto("Curso de Español para Principiantes",
                                "Clases de 10 semanas de español básico para refugiados recién llegados",
                                List.of("educación", "integración")),

                        new Proyecto("Mentoría Laboral",
                                "Apoyo para la búsqueda de empleo y elaboración de CV",
                                List.of("empleo", "coaching")),

                        new Proyecto("Colecta de Ropa de Invierno",
                                "Recolección y distribución de abrigos y ropa de invierno",
                                List.of("donación", "ropa")),

                        new Proyecto("Taller de Cocina Comunitaria",
                                "Preparación de platos tradicionales y encuentro intercultural",
                                List.of("comunidad", "alimentación")),

                        new Proyecto("Círculo de Mujeres Refugiadas",
                                "Espacio seguro para mujeres refugiadas, apoyo y talleres",
                                List.of("mujeres", "apoyo", "comunidad")),

                        new Proyecto("Clases de Alfabetización Digital",
                                "Enseñanza de herramientas digitales básicas y uso de internet",
                                List.of("educación", "digital")),

                        new Proyecto("Taller de Costura para Mujeres",
                                "Capacitación en costura y manualidades, empoderamiento femenino",
                                List.of("mujeres", "habilidades", "empleo")),

                        new Proyecto("Programa Deportivo Comunitario",
                                "Actividades deportivas para jóvenes refugiados y familias",
                                List.of("deporte", "integración", "familia")),

                        new Proyecto("Apoyo Psicológico y Social",
                                "Sesiones de acompañamiento psicológico y orientación social",
                                List.of("salud", "apoyo")),

                        new Proyecto("Taller de Arte y Cultura",
                                "Actividades artísticas y culturales para preservar tradiciones y fomentar la integración",
                                List.of("cultura", "arte", "integración")),

                        new Proyecto("Clases de Cocina Halal",
                                "Taller de cocina adaptada a la dieta halal y hábitos culturales",
                                List.of("alimentación", "cultura", "musulmanes")),

                        new Proyecto("Club de Lectura para Mujeres y Niñas",
                                "Fomento de la lectura y aprendizaje en un entorno seguro para mujeres y niñas",
                                List.of("mujeres", "niñas", "educación"))
                );

                proyectoRepo.saveAll(proyectos);
            }
        };
    }
}
