package org.tfg.spring.tfg.domain.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioDTO {
    
    private Long id;

    private String nombre;

    private String dni;

    private String mail; // Agrega el campo para el correo electrónico

    private String contraseña; // Agrega el campo para la contraseña

    private LocalDate fechaAlta; 



}
