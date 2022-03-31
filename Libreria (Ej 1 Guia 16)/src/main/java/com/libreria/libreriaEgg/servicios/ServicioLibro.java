package com.libreria.libreriaEgg.servicios;

import com.libreria.libreriaEgg.entidades.Libro;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.repositorios.RepositorioLibro;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioLibro {

    @Autowired
    private RepositorioLibro repositorioLibro;

    public void crear(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        validar(isbn, titulo, anio, ejemplares);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setAlta(Boolean.TRUE);
        repositorioLibro.save(libro);
    }

    public void modificar(String id, Long isbn, String titulo, Integer anio) throws ErrorServicio {
        validar(isbn, titulo, anio);
        Optional<Libro> respuesta = repositorioLibro.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
        } else {
            throw new ErrorServicio("El libro no existe");
        }

    }
    
    public void agregarEjemplares(String id, Integer ejemplares) throws ErrorServicio {
        Optional<Libro> respuesta = repositorioLibro.findById(id);
        
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setEjemplares(libro.getEjemplares() + ejemplares);
            libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
            repositorioLibro.save(libro);
        } else {
            throw new ErrorServicio("El libro no existe");
        }
    }
    
    public void disminuirEjemplares(String id, Integer ejemplares) throws ErrorServicio {
        Optional<Libro> respuesta = repositorioLibro.findById(id);
        
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setEjemplares(libro.getEjemplares() - ejemplares);
            libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
            repositorioLibro.save(libro);
        } else {
            throw new ErrorServicio("El libro no existe");
        }
    }
    
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Libro> respuesta = repositorioLibro.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.FALSE);
            repositorioLibro.save(libro);
        } else {
            throw new ErrorServicio("No se encontro el libro solicitado.");
        }
    }

    public void habilitar(String id) throws ErrorServicio {
        Optional<Libro> respuesta = repositorioLibro.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(Boolean.TRUE);
            repositorioLibro.save(libro);
        } else {
            throw new ErrorServicio("No se encontro el libro solicitado.");
        }
    }

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        if (isbn == null || isbn.toString().length() != 8) {
            throw new ErrorServicio("El número de isbn no es válido");
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ErrorServicio("El titulo no puede estar vacio");
        }

        if (anio == null || anio.toString().length() > 4) {
            throw new ErrorServicio("El año no puede sobrepasar los 4 digitos");
        }

        if (ejemplares == null || ejemplares <= 0) {
            throw new ErrorServicio("La cantidad de ejemplares no pueden ser menor o igual a 0");
        }
    }
    
    //Sobrecarga de Validar, para el modificar
    public void validar(Long isbn, String titulo, Integer anio) throws ErrorServicio {
        if (isbn == null || isbn.toString().length() != 8) {
            throw new ErrorServicio("El número de isbn no es válido");
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ErrorServicio("El titulo no puede estar vacio");
        }

        if (anio == null || anio.toString().length() > 4) {
            throw new ErrorServicio("El año no puede sobrepasar los 4 digitos");
        }
    }

    
    /* Metodo general de habilitar/deshabilitar para preguntarle a la Pili
    public void cambiarAlta(String id) throws ErrorServicio {
        Optional<Libro> respuesta = repositorioLibro.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(!libro.getAlta());
            repositorioLibro.save(libro);
        } else {
            throw new ErrorServicio("No se encontro el libro solicitado.");
        }
    } 
    */
    
}
