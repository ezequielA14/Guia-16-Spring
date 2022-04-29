package com.libreria.libreriaEgg.servicios;

import com.libreria.libreriaEgg.entidades.Editorial;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.repositorios.RepositorioEditorial;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioEditorial {

    @Autowired
    private RepositorioEditorial repositorioEditorial;

    @Transactional(propagation = Propagation.NESTED)
    public void guardar(String nombre) throws ErrorServicio {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);
        repositorioEditorial.save(editorial);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, String nombre) throws ErrorServicio {
        validar(nombre);
        Optional<Editorial> respuesta = repositorioEditorial.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            repositorioEditorial.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void borrar(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = repositorioEditorial.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            repositorioEditorial.delete(editorial);
        } else {
            throw new ErrorServicio("La editorial no existe.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = repositorioEditorial.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(Boolean.FALSE);
            repositorioEditorial.save(editorial);
        } else {
            throw new ErrorServicio("No se encontro el autor solicitado.");
        }
    }

    @Transactional(propagation = Propagation.NESTED)
    public void habilitar(String id) throws ErrorServicio {
        Optional<Editorial> respuesta = repositorioEditorial.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setAlta(Boolean.TRUE);
            repositorioEditorial.save(editorial);
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
    
    public Editorial buscarPorId(String id) {
        Optional<Editorial> respuesta = repositorioEditorial.findById(id);
        Editorial editorial = respuesta.get();
        return editorial;
    }

    @Transactional(readOnly = true)
    public List<Editorial> mostrarTodos() {
        return repositorioEditorial.findAll();
    }

    @Transactional(readOnly = true)
    public List<Editorial> mostrarTodosAlta() {
        return repositorioEditorial.buscarPorAlta(Boolean.TRUE);
    }

    @Transactional(readOnly = true)
    public List<Editorial> mostrarPorNombre(String nombre) {
        return repositorioEditorial.buscarPorNombre(nombre);
    }
}
