package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Estudiante;
import com.example.services.EstudianteService;

@Controller
@RequestMapping("/")

public class MainController {

    @Autowired
    private EstudianteService estudianteService;
    //Objeto de tipo estudiante service (interface), autowired nos lo inyecta

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
     */
    //No vamos a usar ModelAndBView por cambiar un poco, nos va a dar un String para
    //luego enseñarlo en la vista 
    
    @GetMapping("/frmAltaEstudiante")
    public String formularioAltaEstudiante(Model model) {

        model.addAttribute("estudiante", new Estudiante());

        return "views/formularioAltaEstudiante"; //tenemos que poner la vista (crearla) donde está
    } 

}
