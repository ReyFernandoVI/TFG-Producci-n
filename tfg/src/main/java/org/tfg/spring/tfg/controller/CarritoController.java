package org.tfg.spring.tfg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tfg.spring.tfg.domain.Carrito;
import org.tfg.spring.tfg.domain.CarritoZapatillas;
import org.tfg.spring.tfg.domain.vm.ZapatillaCantidad;
import org.tfg.spring.tfg.service.CarritoService;

import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/api")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/carritos/find-last")
    public Carrito getCarritroUsuario(HttpSession s) {
        return carritoService.findCarritoByUsuarioId(s);
    }

    @GetMapping("/carritos/item-count")
    public int getCarritoItemCount(HttpSession s) {
        Carrito carrito = carritoService.findCarritoByUsuarioId(s);
        int count = 0;
        if(carrito == null) {
            // Manejar el caso donde carrito es null devolviendo un valor predeterminado
            return 0;
        }
        for(CarritoZapatillas carritoZapatillas :carrito.getCarritoZapatillas()){
            count += carritoZapatillas.getCantidad();
        }
        return count;
    }
    
    @PostMapping("/carritos/update")
    public Carrito postMethodName(@RequestBody ZapatillaCantidad zapatillaCantidad, HttpSession s) {
        return carritoService.updateSaveCrarito(zapatillaCantidad, s);
    }

    @GetMapping("/carritos/finalize")
    public void finalizarCompra(HttpSession s) {
        carritoService.finalizarCompra(s);
    }

    @GetMapping("/carritos/cancel")
    public void cancelarCompra(
        @RequestParam("carritoZapatillasId") List<Long> carritoZapatillasId,
        @RequestParam("carritoId") Long carritoId,
        HttpSession s) {
        

        carritoService.cancelarCompra(carritoZapatillasId, carritoId, s);    
        // carritoService.cancelarCompra(s);
    }
    
}
