package org.tfg.spring.tfg.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tfg.spring.tfg.exception.DangerException;
import org.tfg.spring.tfg.helper.PRG;
import org.tfg.spring.tfg.service.MarcaService;
import org.tfg.spring.tfg.service.ModeloService;
import org.tfg.spring.tfg.service.ZapatillaService;


@RequestMapping("/zapatilla")
@Controller
public class ZapatillaController {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ZapatillaService zapatillaService;

    @Autowired
    private MarcaService marcaService;

    private static final String LOAD_DIR = "src\\main\\resources\\static\\img";

    @GetMapping("r")
    public String r(
            ModelMap m,@Param("PalabraClave")String palabraClave) {
        m.put("zapatillas", zapatillaService.findAll(palabraClave));
        m.put("palabraClave",palabraClave);
        m.put("view", "zapatilla/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(
            ModelMap m) {
    
        m.put("marcas", marcaService.findAll());
        m.put("modelos", modeloService.findAll());
        m.put("view", "zapatilla/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("color") String color,
            @RequestParam("talla") String talla,
            @RequestParam("stock") Integer stock,
            @RequestParam("idMarca") Long idMarca,
            @RequestParam("idModelo") Long idModelo,
            @RequestParam("imagenZapatilla") MultipartFile imagenZapatilla
            ) throws DangerException
             {if(!imagenZapatilla.isEmpty()){
        try {
            
            String imageName = imagenZapatilla.getOriginalFilename();
            Path destPath = Paths.get(LOAD_DIR, imageName);
            Files.copy(imagenZapatilla.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
            String nombreImagen = imageName.substring(imageName.lastIndexOf("\\") + 1, imageName.lastIndexOf("."));
            zapatillaService.save(nombre, precio, color, talla, stock, idMarca, idModelo, nombreImagen);
        } catch (Exception e) {
            e.printStackTrace();
            PRG.error("El Producto " + nombre + " ya existe", "/zapatilla/c");
        }
        return "redirect:/admin";
    }else{
        return "Image Empty";
    }
    }

    @GetMapping("u")
    public String update(
            @RequestParam("id") Long idZapatilla,
            ModelMap m) {
        m.put("zapatilla", zapatillaService.findById(idZapatilla));
        m.put("marcas", marcaService.findAll());
        m.put("modelos", modeloService.findAll());
        m.put("view", "zapatilla/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String updatePost(
            @RequestParam("idZapatilla") Long idZapatilla,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") double precio,
            @RequestParam("color") String color,
            @RequestParam("talla") String talla,
            @RequestParam("stock") Integer stock,
            @RequestParam("imagenZapatilla") MultipartFile imagenZapatilla,
            @RequestParam("idMarca") Long idMarca,
            @RequestParam("idModelo") Long idModelo
            ) throws DangerException {
        try {
            String imageName = imagenZapatilla.getOriginalFilename();
            Path destPath = Paths.get(LOAD_DIR, imageName);
            Files.copy(imagenZapatilla.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
            String nombreImagen = imageName.substring(imageName.lastIndexOf("\\") + 1, imageName.lastIndexOf("."));
            zapatillaService.update(idZapatilla, nombre, precio, color, talla, stock, nombreImagen, idMarca, idModelo);
        } catch (Exception e) {
            PRG.error("El Producto" + nombre + " ya está registrado", "/zapatilla/r");
        }
        return "redirect:/zapatilla/r";
    }

    @PostMapping("d")
    public String delete(
            @RequestParam("id") Long idZapatilla) throws DangerException {
        try {
            zapatillaService.delete(idZapatilla);
        } catch (Exception e) {
            PRG.error(e.getMessage(), "/zapatilla/r");
        }
        return "redirect:/zapatilla/r";
    }

}
