package com.libreria.libreriaEgg.controladores;

import com.libreria.libreriaEgg.entidades.Editorial;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.servicios.ServicioEditorial;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class ControladorEditorial {

    @Autowired
    private ServicioEditorial servicioEditorial;

    @GetMapping("/registro")
    public String registro() {
        return "editorial/editorial_registro";
    }

    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String nombre) throws Exception {
        try {
            servicioEditorial.guardar(nombre);
            System.out.println("Nombre: " + nombre);
            modelo.put("exito", "La editorial se ha registrado correctamente.");
        } catch (ErrorServicio e) {
            e.printStackTrace();
            modelo.put("error", "El registro ha fallado.");
        }
        return "editorial/editorial_registro";
    }

    @GetMapping("/gestion")
    public String gestion(ModelMap modelo) {
        List<Editorial> editoriales = servicioEditorial.mostrarTodos();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial/editorial_gestion";
    }

    @GetMapping("/editar")
    public String editar(ModelMap modelo, @RequestParam("id") String id) {
        Editorial editorial = servicioEditorial.buscarPorId(id);
        modelo.put("editorial", editorial);
        return "editorial/editorial_editar";
    }

    @PostMapping("/editar")
    public String editar(ModelMap modelo, @RequestParam String id, @RequestParam String nombre) {
        try {
            servicioEditorial.modificar(id, nombre);
            modelo.put("exito", "La editorial se modificó con exito!");
        } catch (ErrorServicio e) {
            modelo.put("error", "No se pudo modificar correctamente...");
        }
        Editorial editorial = servicioEditorial.buscarPorId(id);
        modelo.put("editorial", editorial);
        return "editorial/editorial_editar";
    }

    @GetMapping("/alta")
    public String darAlta(ModelMap modelo, @RequestParam("id") String id) {
        try {
            servicioEditorial.habilitar(id);
        } catch (ErrorServicio e) {
            e.printStackTrace();
            modelo.put("error", "No se ha podido habilitar la editorial");
        }
        return "redirect:/editorial/gestion";
    }

    @GetMapping("/baja")
    public String darBaja(ModelMap modelo, @RequestParam("id") String id) {
        try {
            servicioEditorial.deshabilitar(id);
        } catch (ErrorServicio e) {
            e.printStackTrace();
            modelo.put("error", "No se ha podido deshabilitar la editorial");
        }
        return "redirect:/editorial/gestion";
    }
}
