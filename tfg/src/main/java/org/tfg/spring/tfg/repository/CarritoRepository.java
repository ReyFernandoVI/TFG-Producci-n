package org.tfg.spring.tfg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.spring.tfg.domain.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long>{
    
    public Optional<Carrito> findFirstByUsuarioIdAndIsBoughtFalse(Long usuarioId);
}
