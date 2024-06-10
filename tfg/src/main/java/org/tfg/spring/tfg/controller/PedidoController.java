package org.tfg.spring.tfg.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tfg.spring.tfg.domain.Pedido;
import org.tfg.spring.tfg.domain.Zapatilla;
import org.tfg.spring.tfg.exception.DangerException;
import org.tfg.spring.tfg.helper.PRG;
import org.tfg.spring.tfg.service.MarcaService;
import org.tfg.spring.tfg.service.ModeloService;
import org.tfg.spring.tfg.service.PedidoService;
import org.tfg.spring.tfg.service.ZapatillaService;

@RequestMapping("/pedido")
@Controller
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;
   // Declaración de precioTotal como atributo de la clase
   @Autowired
   private ZapatillaService zapatillaService;

   @Autowired
   private MarcaService marcaService;

   @Autowired
   private ModeloService modeloService;

   private static final String LOAD_DIR = "src\\main\\resources\\static\\img";
   
    @GetMapping("r")
    public String r(ModelMap m) {
        List<Pedido> pedidos = pedidoService.findAll();
        m.put("pedidos", pedidos);
        m.put("cantidadTotal", pedidos.size());
       
        m.put("view", "pedido/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(ModelMap m) {
        m.put("marcas", marcaService.findAll());
        m.put("modelos", modeloService.findAll());
        m.put("view", "pedido/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
        @RequestParam("nombreZapatilla") String nombreZapatilla,
        @RequestParam("modeloZapatilla") String modeloZapatilla,
        @RequestParam("marcaZapatilla") String marcaZapatilla,
        @RequestParam("precioUnidad") double precioUnidad,
        @RequestParam("fechCompra") LocalDate fechCompra,
        @RequestParam("cantidadZapatilla") Integer cantidad,
        @RequestParam("color") String color,
        @RequestParam("talla") String talla,
        @RequestParam("imagenZapatilla") MultipartFile imagenZapatilla
    ) throws DangerException {
        try {
            Long idMarca = zapatillaService.findIdMarca(marcaZapatilla);
            Long idModelo = zapatillaService.findIdModelo(modeloZapatilla);
            double precioUnidadTotal = pedidoService.calcularCantidadPrecio(precioUnidad, cantidad);

            // Manejo del archivo de imagen
            if (!imagenZapatilla.isEmpty()) {
                String imageName = imagenZapatilla.getOriginalFilename();
                Path destPath = Paths.get(LOAD_DIR, imageName);

                try (InputStream inputStream = imagenZapatilla.getInputStream()) {
                    Files.copy(inputStream, destPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new DangerException("Error al copiar la imagen: " + e.getMessage());
                }
                String nombreImagen = imageName.substring(imageName.lastIndexOf("\\") + 1, imageName.lastIndexOf("."));

                pedidoService.save(nombreZapatilla, modeloZapatilla, marcaZapatilla, precioUnidad, fechCompra, precioUnidadTotal, cantidad, color, talla, nombreImagen);

                Optional<Zapatilla> zapatillaExistente = zapatillaService.findByName(nombreZapatilla);

                if (zapatillaExistente.isPresent()) {
                    Zapatilla zapatilla = zapatillaExistente.get();
                    if (zapatilla.getColor().equals(color) && zapatilla.getTalla().equals(talla)) {
                        zapatillaService.updateStock(zapatilla, cantidad);
                    } else {
                        zapatillaService.save(nombreZapatilla, precioUnidad, color, talla, cantidad, idMarca, idModelo, nombreImagen);
                    }
                } else {
                    zapatillaService.save(nombreZapatilla, precioUnidad, color, talla, cantidad, idMarca, idModelo, nombreImagen);
                }
            } else {
                PRG.error("Imagen vacía, por favor seleccione una imagen válida", "/pedido/c");
            }
        } catch (DangerException e) {
            PRG.error("Error al crear el pedido: " + e.getMessage(), "/pedido/c");
        } catch (Exception e) {
            PRG.error("Error inesperado: " + e.getMessage(), "/pedido/c");
        }
        return "redirect:/pedido/r";
    }

    @GetMapping("u")
    public String update(
        @RequestParam("id") Long idPedido,
        ModelMap m
    ) {
        m.put("pedido", pedidoService.findById(idPedido));
        m.put("view", "pedido/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String updatePost(
        @RequestParam("id") Long idPedido
    ) throws DangerException {
        try {
            pedidoService.update(idPedido);
        } catch (Exception e) {
            PRG.error("Error al actualizar el pedido: " + e.getMessage(), "/pedido/r");
        }
        return "redirect:/pedido/r";
    }

    @PostMapping("d")
    public String delete(
        @RequestParam("id") Long idPedido
    ) throws DangerException {
        try {
            pedidoService.delete(idPedido);
        } catch (Exception e) {
            PRG.error("Error al eliminar el pedido: " + e.getMessage(), "/pedido/r");
        }
        return "redirect:/pedido/r";
    }
}
