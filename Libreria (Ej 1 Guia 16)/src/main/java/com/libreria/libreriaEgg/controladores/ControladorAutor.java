package com.libreria.libreriaEgg.controladores;

import com.libreria.libreriaEgg.entidades.Autor;
import com.libreria.libreriaEgg.errores.ErrorServicio;
import com.libreria.libreriaEgg.servicios.ServicioAutor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class ControladorAutor {

    @Autowired
    private ServicioAutor servicioAutor;

    @GetMapping("/registro")
    public String registro() {
        return "autor/autor_registro";
    }

    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String nombre) throws ErrorServicio {
        try {
            servicioAutor.guardar(nombre);
            modelo.put("exito", "El autor se ha registrado correctamente.");
        } catch (ErrorServicio e) {
            modelo.put("error", "El registro ha fallado.");
        }
        return "autor/autor_registro";
    }

    @GetMapping("/gestion")
    public String gestion(ModelMap modelo) {
        List<Autor> autores = servicioAutor.mostrarTodos();
        modelo.addAttribute("autores", autores);
        return "autor/autor_gestion";
    }

    @GetMapping("/editar")
    public String editar(ModelMap modelo, @RequestParam("id") String id) {
        Autor autor = servicioAutor.buscarPorId(id);
        modelo.put("autor", autor);
        return "autor/autor_editar";
    }

    @PostMapping("/editar") /// no aparecen los mensajes pero funciona
    public String editar(ModelMap modelo, @RequestParam String id, @RequestParam String nombre) {
        try {
            servicioAutor.modificar(id, nombre);
            modelo.put("exito", "El autor se modificó con exito!");
        } catch (ErrorServicio e) {
            modelo.put("error", "No se pudo modificar correctamente...");
        }
        Autor autor = servicioAutor.buscarPorId(id);
        modelo.put("autor", autor);
        return "autor/autor_editar";
    }

    @GetMapping("/alta")
    public String darAlta(ModelMap modelo, @RequestParam("id") String id) {
        try {
            servicioAutor.habilitar(id);
        } catch (ErrorServicio e) {
            e.printStackTrace();
//            modelo.put("error", "No se ha podido habilitar el autor");
        }
        return "redirect:/autor/gestion";
    }

    @GetMapping("/baja")
    public String darBaja(ModelMap modelo, @RequestParam("id") String id) {
        try {
            servicioAutor.deshabilitar(id);
        } catch (ErrorServicio e) {
            e.printStackTrace();
//            modelo.put("error", "No se ha podido deshabilitar el autor");
        }
        return "redirect:/autor/gestion";
    }
}
