package org.tfg.spring.tfg.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.spring.tfg.domain.Modelo;


@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long>{
  public Modelo findByNombre(String nombre);}
