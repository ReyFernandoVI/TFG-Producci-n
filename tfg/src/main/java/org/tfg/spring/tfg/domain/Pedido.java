package org.tfg.spring.tfg.domain;

import java.time.LocalDate;
import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numPedido;
    private String nombreZapatilla;
    private String modeloZapatilla;
    private String marcaZapatilla;
    private double precioUnidad;
    private double precioTotal;
    private LocalDate fechCompra;
    private Integer stock;
    
    private String color;

    private String talla;


    private String imagen;



    public Pedido(String nombreZapatilla, String modeloZapatilla, String marcaZapatilla, double precioUnidad, LocalDate fechCompra,double precioTotal, Integer stock,String color,String talla,String Image) {

        this.nombreZapatilla = nombreZapatilla;
        this.modeloZapatilla = modeloZapatilla;
        this.marcaZapatilla = marcaZapatilla;
        this.precioUnidad = precioUnidad;
        this.fechCompra = fechCompra;
        this.precioTotal=precioTotal;
        this.numPedido = generateRandomNumber();
        this.stock=stock;
        this.color=color;
        this.talla=talla;
        
    }

   

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(100001); 
    }
}
