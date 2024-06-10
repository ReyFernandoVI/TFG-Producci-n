package org.tfg.spring.tfg.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tfg.spring.tfg.domain.Zapatilla;
import org.tfg.spring.tfg.repository.MarcaRepository;
import org.tfg.spring.tfg.repository.ModeloRepository;
import org.tfg.spring.tfg.repository.ZapatillaRepository;

@Service
public class ZapatillaService {

    @Autowired
    private ModeloRepository modeloRepository;
    
    @Autowired
    private ZapatillaRepository zapatillaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Zapatilla> findAll(String palabraClave) {
        if(palabraClave!=null)
        {
            return zapatillaRepository.findAll(palabraClave);
        }
        return zapatillaRepository.findAll();
    }
    public void save(String nombre,double precio,String color, String talla, Integer stock, Long idMarca, Long idModelo, String nombreImagen) {
        Zapatilla zapatilla = new Zapatilla(nombre, precio, color, talla, stock);
        zapatilla.setMarcas(marcaRepository.getReferenceById(idMarca));
        zapatilla.setModelo(modeloRepository.getReferenceById(idModelo));
        zapatilla.setImagen(nombreImagen);
        zapatillaRepository.save(zapatilla);
     }
    public Zapatilla findById(Long idZapatilla) {
        return zapatillaRepository.findById(idZapatilla).get();
    }
    public Long findIdModelo(String nombre)
    {
      return modeloRepository.findByNombre(nombre).getId();
    }
    public Long findIdMarca(String nombre)
    {
      return marcaRepository.findByNombre(nombre).getId();
    }
    public Optional<Zapatilla> findByName(String nombreZapatilla) {
        // Suponiendo que tienes un repositorio `zapatillaRepository` con un método `findByName`
        return zapatillaRepository.findByNombre(nombreZapatilla);
    }

    public void updateStock(Zapatilla zapatilla, int cantidad) {
        zapatilla.setStock(zapatilla.getStock() + cantidad);
        zapatillaRepository.save(zapatilla);
    }
    public void update(Long idZapatilla, String nombre, double precio,String color, String talla, Integer stock, String nombreImagen, Long idMarca, Long idModelo) {
        Zapatilla zapatilla = zapatillaRepository.findById(idZapatilla).get();
        zapatilla.setNombre(nombre);
        zapatilla.setPrecio(precio);
        zapatilla.setColor(color);
        zapatilla.setTalla(talla);
        zapatilla.setStock(stock);
        zapatilla.setImagen(nombreImagen);
        zapatilla.setMarcas(marcaRepository.getReferenceById(idMarca));
        zapatilla.setModelo(modeloRepository.getReferenceById(idModelo));
        zapatillaRepository.save(zapatilla);
    }
    public void delete(Long idZapatilla) {
        zapatillaRepository.delete(zapatillaRepository.getReferenceById(idZapatilla));
    }

    public void init() {
        if (zapatillaRepository.count() == 0) {
            

            Zapatilla zapatilla = new Zapatilla();
            Zapatilla zapatilla1 = new Zapatilla();
            Zapatilla zapatilla2 = new Zapatilla();
            Zapatilla zapatilla3 = new Zapatilla();

            List <Zapatilla> zapatillasIni = new ArrayList<Zapatilla>();
        
            zapatilla.setNombre("Sky Blue");
            zapatilla.setPrecio(120);
            zapatilla.setColor("Azul");
            zapatilla.setTalla("42");
            zapatilla.setStock(2);
            zapatilla.setMarcas(marcaRepository.getReferenceById(1L));
            zapatilla.setModelo(modeloRepository.getReferenceById(1L));
            zapatilla.setImagen("AirMax1Cielo");

            zapatilla1.setNombre("Dragon Boost");
            zapatilla1.setPrecio(220);
            zapatilla1.setColor("Gris");
            zapatilla1.setTalla("41");
            zapatilla1.setStock(3);
            zapatilla1.setMarcas(marcaRepository.getReferenceById(2L));
            zapatilla1.setModelo(modeloRepository.getReferenceById(2L));
            zapatilla1.setImagen("UltraBoost3");

            zapatilla2.setNombre("Fruit Air");
            zapatilla2.setPrecio(100);
            zapatilla2.setColor("Azul-Salmon");
            zapatilla2.setTalla("42");
            zapatilla2.setStock(1);
            zapatilla2.setMarcas(marcaRepository.getReferenceById(1L));
            zapatilla2.setModelo(modeloRepository.getReferenceById(1L));
            zapatilla2.setImagen("AirMax1Anuncio");

            zapatilla3.setNombre("Green Valley");
            zapatilla3.setPrecio(130);
            zapatilla3.setColor("Verde");
            zapatilla3.setTalla("45");
            zapatilla3.setStock(1);
            zapatilla3.setMarcas(marcaRepository.getReferenceById(3L));
            zapatilla3.setModelo(modeloRepository.getReferenceById(3L));
            zapatilla3.setImagen("NbGreen");
          
            zapatillasIni.add(zapatilla);
            zapatillasIni.add(zapatilla1);
            zapatillasIni.add(zapatilla2);
            zapatillasIni.add(zapatilla3);

            zapatillaRepository.saveAll(zapatillasIni);
        }
    }  

    

}
