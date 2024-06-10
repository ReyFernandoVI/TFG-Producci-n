package org.tfg.spring.tfg.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Zapatilla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
    private String nombre;

    private double precio;

    private String color;

    private String talla;

    private Integer stock;

    @ManyToOne
    private Marca marcas;//SELECT MARCA PARA CREAR ZAPATILLA

    @ManyToOne
    private Modelo modelo;

    private String imagen;
    
    // ==================

    public Zapatilla(String nombre,double precio,String color, String talla, Integer stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.color = color;
        this.talla = talla;
        this.stock = stock;
    }
}
