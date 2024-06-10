package org.tfg.spring.tfg.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tfg.spring.tfg.exception.DangerException;
import org.tfg.spring.tfg.helper.PRG;
import org.tfg.spring.tfg.service.MarcaService;
import org.tfg.spring.tfg.service.ModeloService;

@RequestMapping("/marca")
@Controller
public class MarcaController {

    @Autowired
    private MarcaService marcaService;
    @Autowired
    private ModeloService modeloService;

    @GetMapping("r")
    public String r(
            ModelMap m) {
                
        m.put("marcas", marcaService.findAll());
       
        m.put("view", "marca/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(
            ModelMap m) {
        m.put("marcas", marcaService.findAll());
        m.put("modelos", modeloService.findAll());
        m.put("view", "marca/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
            @RequestParam("nombre") String nombre)
            throws DangerException {
        try {
            marcaService.save(nombre);
        } catch (Exception e) {
            PRG.error("La Marca " + nombre + " ya existe", "/marca/c");
        }
        return "redirect:/marca/r";
    }

    @GetMapping("u")
    public String update(
            @RequestParam("id") Long idMarca,
            ModelMap m) {
        m.put("marca", marcaService.findById(idMarca));
        m.put("view", "marca/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String updatePost(
            @RequestParam("idMarca") Long idMarca,
            @RequestParam("nombre") String nombre) throws DangerException {
        try {
            marcaService.update(idMarca, nombre);
        } catch (Exception e) {
            PRG.error("La Marca" + nombre + " ya está registrado", "/marca/r");
        }
        return "redirect:/marca/r";
    }

    @PostMapping("d")
    public String delete(
            @RequestParam("id") Long idMarca) throws DangerException {
        try {
            marcaService.delete(idMarca);
        } catch (Exception e) {
            PRG.error(e.getMessage(), "/marca/r");
        }
        return "redirect:/marca/r";
    }

    @GetMapping("listaModelo")
    public String listaModelo(
            @RequestParam("id") Long idMarca,
            ModelMap m) {
        m.put("marca", marcaService.findById(idMarca));
        m.put("view", "marca/listaModelo");
        return "_t/frame";
    }

}
