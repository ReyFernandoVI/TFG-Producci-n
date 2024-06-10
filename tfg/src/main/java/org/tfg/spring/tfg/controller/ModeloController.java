package org.tfg.spring.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tfg.spring.tfg.domain.Marca;
import org.tfg.spring.tfg.exception.DangerException;
import org.tfg.spring.tfg.helper.PRG;
import org.tfg.spring.tfg.service.MarcaService;
import org.tfg.spring.tfg.service.ModeloService;

@RequestMapping("/modelo")
@Controller
public class ModeloController {

    @Autowired
    private ModeloService modeloService;
    @Autowired
    private MarcaService  marcaService;

    @GetMapping("r")
    public String r(
            ModelMap m) {
        m.put("modelos", modeloService.findAll());
        m.put("marcas", marcaService.findAll());
        
        m.put("view", "modelo/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(
            ModelMap m) {
        m.put("modelos", modeloService.findAll());
        m.put("marcas", marcaService.findAll());
        m.put("view", "modelo/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
            @RequestParam("nombre") String nombre, @RequestParam("marcaId") Marca marcaId)
            throws DangerException {
        try {
            modeloService.save(nombre,marcaId);
        } catch (Exception e) {
            PRG.error("El Modelo " + nombre + " ya existe", "/modelo/c");
        }
        return "redirect:/modelo/r";
    }

    @GetMapping("u")
    public String update(
            @RequestParam("id") Long idModelo,
            ModelMap m) {
        m.put("modelo", modeloService.findById(idModelo));
        m.put("view", "modelo/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String updatePost(
            @RequestParam("id") Long idModelo,
            @RequestParam("nombre") String nombre) throws DangerException {
        try {
            modeloService.update(idModelo, nombre);
        } catch (Exception e) {
            PRG.error("El Modelo " + nombre + " ya está registrado", "/marca/r");
        }
        return "redirect:/modelo/r";
    }

    @PostMapping("d")
    public String delete(
            @RequestParam("id") Long idModelo) throws DangerException {
        try {
            modeloService.delete(idModelo);
        } catch (Exception e) {
            PRG.error(e.getMessage(), "/modelo/r");
        }
        return "redirect:/modelo/r";
    }

}
