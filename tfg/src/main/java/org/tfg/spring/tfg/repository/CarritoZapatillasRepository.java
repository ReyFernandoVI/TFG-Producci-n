package org.tfg.spring.tfg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.spring.tfg.domain.CarritoZapatillas;

@Repository
public interface CarritoZapatillasRepository extends JpaRepository<CarritoZapatillas, Long>{
    public void deleteByCarritoId(Long carritoId);
}

