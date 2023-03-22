package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;

@Controller
@RequestMapping("/")

public class MainController {

    @Autowired
    private EstudianteService estudianteService;
    //Objeto de tipo estudiante service (interface), autowired nos lo inyecta

    @Autowired
    private FacultadService facultadService;

    //Un controlador en el patrón mvc de spring responde una request concreta, 
    //y la delega posteriormente en un método que tiene en cuenta el verbo utilizado 
    //del protocolo htto para hacer la peticion
    //Los verbos (get, post, put, delete, option, entre otros)

    /** 
     * El metodo siguiente devuelve un listado de estudiantes
     */
    @GetMapping("/listar") //Este metodo recibe las peticiones y la url que respone y va a entrar en accion el metodo
    public ModelAndView listar() {

        //Pedirle a servicio de estudiante que me devuelva listado de estudiantes
        List<Estudiante> estudiantes = estudianteService.findAll(); 

        ModelAndView mav = new ModelAndView("views/listarEstudiantes");
        mav.addObject("estudiantes", estudiantes); 
        
        return mav;
    }

    /** 
     * Metodo para mostrar el formulario de alta de estudiante
     * Necesitamos meter tambien la lista de facultades
     */
    //No vamos a usar ModelAndBView por cambiar un poco, nos va a dar un String para
    //luego enseñarlo en la vista 
    
    @GetMapping("/frmAltaEstudiante")
    public String formularioAltaEstudiante(Model model) {

        List<Facultad> facultades = facultadService.findAll();

        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("facultades", facultades);

        return "views/formularioAltaEstudiante"; //tenemos que poner la vista (crearla) donde está
    } 

    /**
     * Metodo que recibe los datos procedentes de los controles del formulario
     */
    @PostMapping("/altaEstudiante")
    public String altaEstudiante(@ModelAttribute Estudiante estudiante) {

        //guardarlo en la base de datos
        estudianteService.save(estudiante);

        return "redirect:/listar"; 
    }

}
