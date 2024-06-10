package org.tfg.spring.tfg.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tfg.spring.tfg.domain.Marca;
import org.tfg.spring.tfg.domain.Modelo;
import org.tfg.spring.tfg.repository.MarcaRepository;
import org.tfg.spring.tfg.repository.ModeloRepository;

@Service
public class ModeloService {
    
    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;
    
    public List<Modelo> findAll() {
        return modeloRepository.findAll();
    }
    public void save(String nombre,Marca idMarca) {
        Modelo modelo = new Modelo(nombre,idMarca);
        modeloRepository.save(modelo);
     }
    public Modelo findById(Long idModelo) {
        return modeloRepository.findById(idModelo).get();
    }

    public Modelo findByNombre(String nombre) {
        return modeloRepository.findByNombre(nombre);
    }

    public void update(Long idModelo, String nombre) {
        Modelo modelo = modeloRepository.findById(idModelo).get();
        modelo.setId(idModelo);
        modelo.setNombre(nombre);
        modeloRepository.save(modelo);
    }
    public void delete(Long idModelo) {
        modeloRepository.delete(modeloRepository.getReferenceById(idModelo));
    }

    public void init() {
        if (modeloRepository.count() == 0) {
            Marca Nike = marcaRepository.findById(1L).orElse(null); // Asumiendo que Nike tiene ID 1
            Marca Adidas = marcaRepository.findById(2L).orElse(null); // Asumiendo que Adidas tiene ID 2
            Marca nb = marcaRepository.findById(3L).orElse(null); // Asumiendo que Adidas tiene ID 2
            Marca Yeezy = marcaRepository.findById(4L).orElse(null); // Asumiendo que Adidas tiene ID 2

            List<Modelo> modelosIni = Arrays.asList(
                new Modelo("AirMax", Nike),
                new Modelo("UltraBoost", Adidas),
                new Modelo("550", nb),
                new Modelo("Beluga", Yeezy)
                // Agrega más modelos si es necesario
            );
            modeloRepository.saveAll(modelosIni);
        }
    }
   

}
