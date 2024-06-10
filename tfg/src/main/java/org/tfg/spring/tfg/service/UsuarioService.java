package org.tfg.spring.tfg.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tfg.spring.tfg.domain.Usuario;
import org.tfg.spring.tfg.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
    
    public Usuario save(String nombre, String dni,String mail, String contraseña) {
        
        Usuario usuario = new Usuario(nombre, dni, mail, contraseña);
        usuario.setNombre(nombre);
        usuario.setDni(dni);
        usuario.setMail(mail);
        String contraseñaCodificada = new BCryptPasswordEncoder().encode(contraseña);
        usuario.setContraseña(contraseñaCodificada);
        usuario.setFechaAlta(LocalDate.now()); // Establece la fecha de alta como la fecha actual
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    public void update(Long idUsuario, String nombre, String dni, String mail) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario != null) {
            usuario.setNombre(nombre);
            usuario.setDni(dni);
            usuario.setMail(mail);
            usuarioRepository.save(usuario);
        }
    }

    public void delete(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Método login
    public Usuario login(String nombre, String contraseña) throws Exception {
        Usuario usuario = usuarioRepository.getByNombre(nombre);
        if (usuario == null) {
            throw new Exception("El Usuario " + nombre + " no existe");
        }
        if (!passwordEncoder.matches(contraseña, usuario.getContraseña())) {
            throw new Exception("La contraseña para el Usuario " + nombre + " es incorrecta");
        }
    
        return usuario;
    }

    public void setAdmin(String nombre) {
        Usuario usuario = usuarioRepository.getByNombre(nombre);
        usuario.setAdmin(true);
        usuarioRepository.save(usuario);
    }

    public Usuario getAuthUser(HttpSession s){
        Usuario usuario = (Usuario) s.getAttribute("usuario");

        return null != usuario ?usuario: null;
    }

   
}
