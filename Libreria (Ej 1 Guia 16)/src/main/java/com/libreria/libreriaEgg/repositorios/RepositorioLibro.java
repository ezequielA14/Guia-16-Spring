package com.libreria.libreriaEgg.repositorios;

import com.libreria.libreriaEgg.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLibro extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public List<Libro> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public Libro buscarPorIsbn(@Param("isbn") Integer isbn);

    @Query("SELECT l FROM Libro l WHERE l.anio = :anio")
    public List<Libro> buscarPorAnio(@Param("anio") Integer anio);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombreAutor")
    public List<Libro> buscarPorAutor(@Param("nombreAutor") String nombreAutor);
    
    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombreEditorial")
    public List<Libro> buscarPorEditorial(@Param("nombreEditorial") String nombreEditorial);
}
