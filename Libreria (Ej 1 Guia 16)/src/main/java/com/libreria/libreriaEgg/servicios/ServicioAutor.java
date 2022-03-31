/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libreria.libreriaEgg.servicios;

import com.libreria.libreriaEgg.entidades.Autor;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.repositorios.RepositorioAutor;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutor {

    @Autowired
    private RepositorioAutor repositorioAutor;

    public List<Autor> buscarTodos() {
        List<Autor> autores = repositorioAutor.findAll();
        return autores;
    }

    public void crear(String nombre) throws ErrorServicio {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setAlta(Boolean.TRUE);
        repositorioAutor.save(autor);
    }

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

    public void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio.");
        }
    }

}
