package com.libreria.libreriaEgg.repositorios;

import com.libreria.libreriaEgg.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioAutor extends JpaRepository<Autor, String>{
    
    @Query("SELECT a FROM autor a WHERE a.nombre = :nombre")
    public List<Autor> buscarPorNombre(@Param("nombre")String nombre);
    
}
