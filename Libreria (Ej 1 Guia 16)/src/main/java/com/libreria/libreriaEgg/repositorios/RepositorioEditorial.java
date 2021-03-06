package com.libreria.libreriaEgg.repositorios;

import com.libreria.libreriaEgg.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEditorial extends JpaRepository<Editorial, String> {
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public List<Editorial> buscarPorNombre(@Param("nombre")String nombre);
    
    @Query("SELECT e FROM Editorial e WHERE e.alta = :bool")
    public List<Editorial> buscarPorAlta(@Param("bool") Boolean bool);
}
