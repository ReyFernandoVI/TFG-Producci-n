package org.tfg.spring.tfg.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Modelo {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

   @ManyToOne
   @JsonIgnoreProperties(value = {"modelos"})
    private Marca marcas;
    
    @OneToMany(mappedBy = "modelo")
    @JsonIgnoreProperties(value = {"modelo"})
    private List<Zapatilla> zapatillas;

    public Modelo(String nombre,Marca marca){
        this.nombre = nombre;
        this.marcas=marca;
        this.zapatillas = new ArrayList<>();
    }

}
