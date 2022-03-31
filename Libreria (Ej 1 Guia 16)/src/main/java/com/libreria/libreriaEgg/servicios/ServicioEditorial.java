package com.libreria.libreriaEgg.servicios;

import com.libreria.libreriaEgg.entidades.Editorial;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.repositorios.RepositorioEditorial;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

public class ServicioEditorial {

    @Autowired
    private RepositorioEditorial repositorioEditorial;

    public void crear(String nombre) throws ErrorServicio {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorial.setAlta(Boolean.TRUE);
        repositorioEditorial.save(editorial);
    }

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

    public void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio.");
        }
    }

}
