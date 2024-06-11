package org.tfg.spring.tfg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tfg.spring.tfg.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    public List<Usuario> findByNombre(String nombre);
    public Usuario getByNombre(String nombre);
    public Usuario getByDni(String dni);
}
