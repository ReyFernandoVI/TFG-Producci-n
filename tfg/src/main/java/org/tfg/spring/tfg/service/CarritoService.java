package org.tfg.spring.tfg.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tfg.spring.tfg.domain.Carrito;
import org.tfg.spring.tfg.domain.CarritoZapatillas;
import org.tfg.spring.tfg.domain.Usuario;
import org.tfg.spring.tfg.domain.Zapatilla;
import org.tfg.spring.tfg.domain.vm.ZapatillaCantidad;
import org.tfg.spring.tfg.repository.CarritoRepository;
import org.tfg.spring.tfg.repository.CarritoZapatillasRepository;

import jakarta.servlet.http.HttpSession;

@Service
@Transactional
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoZapatillasRepository carritoZapatillasRepository;

    @Autowired
    private UsuarioService usuarioService;


    public Carrito updateSaveCrarito(ZapatillaCantidad zapatillaCantidad, HttpSession s){

        Carrito carrito = getCarritoIfExist(s);
        CarritoZapatillas carritoZapatillasToSave = null;

        if(null != carrito){

            List<CarritoZapatillas> zapatillasCarrito = carrito.getCarritoZapatillas();

            for(CarritoZapatillas carritoZapatilla: zapatillasCarrito){
                if(carritoZapatilla.getZapatilla().getId().equals(zapatillaCantidad.getZapatilla().getId())){
                    carritoZapatilla.setCantidad(
                        carritoZapatilla.getCantidad().intValue() + zapatillaCantidad.getCantidad().intValue()
                    );

                    carritoZapatillasToSave = carritoZapatilla;
                    break;
                }
            }

            if(null != carritoZapatillasToSave){
                carrito.setCarritoZapatillas(zapatillasCarrito);
                return carritoRepository.save(carrito);
            }


            carritoZapatillasToSave = new CarritoZapatillas();
            carritoZapatillasToSave.setCantidad(zapatillaCantidad.getCantidad());
            carritoZapatillasToSave.setZapatilla(zapatillaCantidad.getZapatilla());
            carritoZapatillasToSave.setCarrito(carrito);
            carritoZapatillasToSave = carritoZapatillasRepository.save(carritoZapatillasToSave);

            return carritoZapatillasToSave.getCarrito();

        }

        Usuario usuario = usuarioService.getAuthUser(s);

        if(null == usuario || null == usuario.getId()) return null; 



        carritoZapatillasToSave = new CarritoZapatillas();
        carritoZapatillasToSave.setCantidad(zapatillaCantidad.getCantidad());
        carritoZapatillasToSave.setZapatilla(zapatillaCantidad.getZapatilla());
        carritoZapatillasToSave = carritoZapatillasRepository.save(carritoZapatillasToSave);

        Carrito newCarrito = new Carrito();

        newCarrito.setUsuario(usuario);
        newCarrito.setCarritoZapatillas(Arrays.asList(carritoZapatillasToSave));
        newCarrito.setIsBought(false);

        newCarrito = carritoRepository.save(newCarrito);
        carritoZapatillasToSave.setCarrito(newCarrito);
        carritoZapatillasToSave = carritoZapatillasRepository.save(carritoZapatillasToSave);
        return newCarrito;
    }

    public void finalizarCompra(HttpSession s){
    
        Carrito carrito = getCarritoIfExist(s);

        if(null == carrito) return;

        carrito.setIsBought(true);
        carritoRepository.save(carrito);
        
    }

    public void cancelarCompra( List<Long> carritoZapatillasId,Long carritoId,HttpSession s){
        for (Long carrito : carritoZapatillasId) {
            carritoZapatillasRepository.deleteById(carrito);
        }


        Carrito carrito = getCarritoIfExist(s);
        if(null == carrito) return;
        
        carritoRepository.deleteById(carritoId);
    }

    public Carrito findCarritoByUsuarioId(HttpSession s){
        return getCarritoIfExist(s);
    }

    private Carrito getCarritoIfExist(HttpSession s){
        Usuario usuario = usuarioService.getAuthUser(s);

        if(null == usuario || null == usuario.getId()) return null; 

        Optional<Carrito> carrito = carritoRepository.findFirstByUsuarioIdAndIsBoughtFalse(usuario.getId());

        return carrito.isPresent()? carrito.get(): null;
    }

}
