package com.libreria.libreriaEgg.controladores;

import com.libreria.libreriaEgg.entidades.Autor;
import com.libreria.libreriaEgg.entidades.Editorial;
import com.libreria.libreriaEgg.entidades.Libro;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.servicios.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class ControladorLibro {

    @Autowired
    private ServicioLibro servicioLibro;
    @Autowired
    private ServicioAutor servicioAutor;
    @Autowired
    private ServicioEditorial servicioEditorial;

    @GetMapping("/registro")
    public String registro(ModelMap modelo) {
        List<Autor> autores = servicioAutor.mostrarTodosAlta();
        List<Editorial> editoriales = servicioEditorial.mostrarTodosAlta();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro/libro_registro";
    }

    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam(defaultValue = "", required = true) Long isbn, @RequestParam(required = true, defaultValue = "") String titulo, @RequestParam(required = true, defaultValue = "") Integer anio, @RequestParam(required = true, defaultValue = "") Integer ejemplares, @RequestParam(required = true, defaultValue = "") Autor autor, @RequestParam(required = true, defaultValue = "") Editorial editorial) throws Exception {
        try {
            servicioLibro.guardar(isbn, titulo, anio, ejemplares, autor, editorial);
            modelo.put("exito", "El libro se ha registrado correctamente.");
            // Las siguientes 6 líneas son para evitar que se registre otro libro repetido
            modelo.put("isbn", "");
            modelo.put("titulo", "");
            modelo.put("anio", "");
            modelo.put("ejemplares", "");
        } catch (ErrorServicio e) {
            e.printStackTrace();
            modelo.put("error", "El registro ha fallado porque: " + e.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
        }

        List<Autor> autores = servicioAutor.mostrarTodosAlta();
        List<Editorial> editoriales = servicioEditorial.mostrarTodosAlta();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro/libro_registro";
    }

    @GetMapping("/gestion")
    public String gestion(ModelMap modelo) {
        List<Libro> libros = servicioLibro.mostrarTodos();
        modelo.addAttribute("libros", libros);
        return "libro/libro_gestion";
    }

    @GetMapping("/editar")
    public String editar(ModelMap modelo, @RequestParam("id") String id) {
        List<Autor> autores = servicioAutor.mostrarTodosAlta();
        List<Editorial> editoriales = servicioEditorial.mostrarTodosAlta();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        Libro libro = servicioLibro.buscarPorId(id);
        modelo.put("libro", libro);
        return "libro/libro_editar";
    }

    @PostMapping("/editar") /// no aparecen los mensajes pero funciona
    public String editar(ModelMap modelo, @RequestParam String id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam Autor autor, @RequestParam Editorial editorial) {
        try {
            servicioLibro.modificar(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
            modelo.put("exito", "El libro se modificó con exito!");
        } catch (ErrorServicio e) {
            modelo.put("error", "No se pudo modificar correctamente...");
        }
        List<Autor> autores = servicioAutor.mostrarTodosAlta();
        List<Editorial> editoriales = servicioEditorial.mostrarTodosAlta();
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        
        Libro libro = servicioLibro.buscarPorId(id);
        modelo.put("libro", libro);
        return "libro/libro_editar";
    }

    @GetMapping("/alta")
    public String darAlta(ModelMap modelo, @RequestParam("id") String id) {
        try {
            servicioLibro.habilitar(id);
        } catch (ErrorServicio e) {
            e.printStackTrace();
//            modelo.put("error", "No se ha podido habilitar el libro");
        }
        return "redirect:/libro/gestion";
    }

    @GetMapping("/baja")
    public String darBaja(ModelMap modelo, @RequestParam("id") String id) {
        try {
            servicioLibro.deshabilitar(id);
        } catch (ErrorServicio e) {
            e.printStackTrace();
//            modelo.put("error", "No se ha podido deshabilitar el libro");
        }
        return "redirect:/libro/gestion";
    }
}
