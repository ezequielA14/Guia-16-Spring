package com.libreria.libreriaEgg.servicios;

import com.libreria.libreriaEgg.entidades.Libro;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.repositorios.RepositorioLibro;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioLibro {

    @Autowired
    private RepositorioLibro repositorioLibro;

    @Transactional(propagation = Propagation.NESTED)
    public void guardar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio {
        validar(isbn, titulo, anio, ejemplares, 0);
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

    @Transactional(propagation = Propagation.NESTED)
    public void modificar(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados) throws ErrorServicio {
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados);
        Optional<Libro> respuesta = repositorioLibro.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplares - ejemplaresPrestados);
        } else {
            throw new ErrorServicio("El libro no existe");
        }

    }

    @Transactional(propagation = Propagation.NESTED)
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

    @Transactional(propagation = Propagation.NESTED)
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

    /// No lleva @Transactional
    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados) throws ErrorServicio {
        if (isbn == null || isbn.toString().length() != 8) {
            throw new ErrorServicio("El número de isbn no es válido");
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ErrorServicio("El titulo no puede estar vacio");
        }

        if (anio == null || anio.toString().length() > 4) {
            throw new ErrorServicio("El año no puede sobrepasar los 4 digitos");
        }

        if (ejemplares == null) {
            throw new ErrorServicio("La cantidad de ejemplares no es valida");
        }

        if (ejemplares < ejemplaresPrestados) {
            throw new ErrorServicio("La cantidad de ejemplares no puede ser menor que los prestados");
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> mostrarTodos() {
        return repositorioLibro.findAll();
    }

    @Transactional(readOnly = true)
    public List<Libro> mostrarPorTitulo(String titulo) {
        return repositorioLibro.buscarPorTitulo(titulo);
    }

    @Transactional(readOnly = true)
    public Libro mostrarPorIsbn(Integer isbn) {
        return repositorioLibro.buscarPorIsbn(isbn);
    }

    @Transactional(readOnly = true)
    public List<Libro> mostrarPorAnio(Integer anio) {
        return repositorioLibro.buscarPorAnio(anio);
    }

    @Transactional(readOnly = true)
    public List<Libro> mostrarPorAutor(String autor) {
        return repositorioLibro.buscarPorAutor(autor);
    }

    @Transactional(readOnly = true)
    public List<Libro> mostrarPorEditorial(String editorial) {
        return repositorioLibro.buscarPorEditorial(editorial);
    }
}