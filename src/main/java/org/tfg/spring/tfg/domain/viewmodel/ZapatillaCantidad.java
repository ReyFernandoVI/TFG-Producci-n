package org.tfg.spring.tfg.domain.viewmodel;

import org.tfg.spring.tfg.domain.Zapatilla;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ZapatillaCantidad {
    
    private Zapatilla zapatilla;

    private Integer cantidad;

    // public Zapatilla getZapatilla() {
    //     return zapatilla;
    // }

    // public void setZapatilla(Zapatilla zapatilla) {
    //     this.zapatilla = zapatilla;
    // }

    // public Integer getCantidad() {
    //     return cantidad;
    // }

    // public void setCantidad(Integer cantidad) {
    //     this.cantidad = cantidad;
    // }
}
