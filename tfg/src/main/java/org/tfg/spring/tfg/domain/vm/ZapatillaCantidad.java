package org.tfg.spring.tfg.domain.vm;

import org.tfg.spring.tfg.domain.Zapatilla;

public class ZapatillaCantidad {
    
    private Zapatilla zapatilla;

    private Integer cantidad;

    public Zapatilla getZapatilla() {
        return zapatilla;
    }

    public void setZapatilla(Zapatilla zapatilla) {
        this.zapatilla = zapatilla;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
