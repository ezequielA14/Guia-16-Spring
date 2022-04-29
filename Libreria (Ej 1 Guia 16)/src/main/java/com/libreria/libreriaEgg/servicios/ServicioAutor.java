package com.libreria.libreriaEgg.servicios;

import com.libreria.libreriaEgg.entidades.Autor;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.repositorios.RepositorioAutor;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioAutor {

    @Autowired
    private RepositorioAutor repositorioAutor;

    @Transactional(propagation = Propagation.NESTED)
    public void guardar(String nombre) throws ErrorServicio {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(Boolean.TRUE);
        repositorioAutor.save(autor);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, String nombre) throws ErrorServicio {
        validar(nombre);
        Optional<Autor> respuesta = repositorioAutor.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            repositorioAutor.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void borrar(String id) throws ErrorServicio {
        Optional<Autor> respuesta = repositorioAutor.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            repositorioAutor.delete(autor);
        } else {
            throw new ErrorServicio("El autor no existe.");
        }
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Autor> respuesta = repositorioAutor.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(Boolean.FALSE);
            repositorioAutor.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void habilitar(String id) throws ErrorServicio {
        Optional<Autor> respuesta = repositorioAutor.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(Boolean.TRUE);
            repositorioAutor.save(autor);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado.");
        }
    }

    /// No lleva @Transactional
    public void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio.");
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> mostrarTodos() {
        return repositorioAutor.findAll();
    }
    
    @Transactional(readOnly = true)
    public Autor buscarPorId(String id) {
        Optional<Autor> respuesta = repositorioAutor.findById(id);
        return respuesta.get();
    }

    @Transactional(readOnly = true)
    public List<Autor> mostrarTodosAlta() {
        return repositorioAutor.buscarPorAlta(Boolean.TRUE);
    }
    
    @Transactional(readOnly = true)
    public List<Autor> mostrarPorNombre(String nombre) {
        return repositorioAutor.buscarPorNombre(nombre);
    }
}