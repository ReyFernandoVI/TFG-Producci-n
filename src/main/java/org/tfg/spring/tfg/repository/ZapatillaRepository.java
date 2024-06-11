package org.tfg.spring.tfg.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.tfg.spring.tfg.domain.Zapatilla;

@Repository
public interface ZapatillaRepository extends JpaRepository<Zapatilla, Long> {

    @Query("SELECT z FROM Zapatilla z " +
           "JOIN z.modelo m " + 
           "JOIN z.marcas s " + 
           "WHERE z.nombre LIKE %:palabraClave% " +
           "OR z.color LIKE %:palabraClave% " +
           "OR z.talla LIKE %:palabraClave% " +
           "OR m.nombre LIKE %:palabraClave% " + 
           "OR s.nombre LIKE %:palabraClave%") 
    List<Zapatilla> findAll(@Param("palabraClave") String palabraClave);
    Optional<Zapatilla> findByNombre(String nombreZapatilla);
}
