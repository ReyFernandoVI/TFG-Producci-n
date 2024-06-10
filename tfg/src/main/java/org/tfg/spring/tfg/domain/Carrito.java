package org.tfg.spring.tfg.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.tfg.spring.tfg.domain.vm.ZapatillaCantidad;

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
public class Carrito{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrito")
    
    private List<CarritoZapatillas> carritoZapatillas = new ArrayList<>();

    @ManyToOne
    private Usuario usuario; 

    private Boolean isBought;

    private LocalDate fechaCompra;

}
