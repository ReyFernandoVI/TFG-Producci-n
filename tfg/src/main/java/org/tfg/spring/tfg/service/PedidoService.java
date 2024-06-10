package org.tfg.spring.tfg.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tfg.spring.tfg.domain.Pedido;
import org.tfg.spring.tfg.repository.PedidoRepository;

@Service
public class PedidoService {

    double precioTotal = 0;

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido save( String nombreZapatilla, String modeloZapatilla, String marcaZapatilla, double precioUnidad, LocalDate fechCompra,double precioTotal,Integer stock,String color,String talla,String nombreImagen) {
        Pedido pedido = new Pedido(nombreZapatilla, modeloZapatilla, marcaZapatilla, precioUnidad, fechCompra,precioTotal,stock,color,talla,nombreImagen);
        pedido.setImagen(nombreImagen);
      
        return pedidoRepository.save(pedido);
    }

    public Pedido findById(Long idPedido) {
        return pedidoRepository.findById(idPedido).orElse(null);
    }

    public void update(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido != null) {
            pedidoRepository.save(pedido);
        }
    }

    public void delete(Long idpedido) {
        pedidoRepository.deleteById(idpedido);
    }

    public List<Pedido> findByNumPedido(Integer numPedido) {
        return pedidoRepository.findByNumPedido(numPedido);
    }

    // Método para calcular el precio total
 
    public double calcularCantidadPrecio(double precioUnidad,Integer cantidadZapatilla)
    {
        double precioUnidadTotal=precioUnidad*cantidadZapatilla;
        return precioUnidadTotal;
    }

  
}
