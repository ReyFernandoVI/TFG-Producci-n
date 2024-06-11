package org.tfg.spring.tfg.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String dni;

    private String mail; // Agrega el campo para el correo electrónico

    private String contraseña; // Agrega el campo para la contraseña

    private LocalDate fechaAlta; // Agrega el campo para la fecha de alta

    private Boolean admin;

    
    // ==================

    public Usuario() {
        this.admin = false;
    }

    public Usuario(String nombre, String dni, String mail, String contraseña) {
        this.nombre = nombre;
        this.dni = dni;
        this.mail = mail;
        this.contraseña = contraseña;
        this.fechaAlta = LocalDate.now();// Establece la fecha de alta como la fecha actual al crear el usuario
        this.admin = false; 
    }
}