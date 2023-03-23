package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

@Controller
@RequestMapping("/")

public class MainController {

    private static final Logger LOG = Logger.getLogger("MainController");

    @Autowired
    private EstudianteService estudianteService;
    //Objeto de tipo estudiante service (interface), autowired nos lo inyecta

    @Autowired
    private FacultadService facultadService;

    @Autowired
    private TelefonoService telefonoService;

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

        Estudiante estudiante = new Estudiante();

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("facultades", facultades);

        return "views/formularioAltaEstudiante"; //tenemos que poner la vista (crearla) donde está
    } 

    /**
     * Metodo que recibe los datos procedentes de los controles del formulario
     */
    @PostMapping("/altaModificacionEstudiante")
    public String altaEstudiante(@ModelAttribute Estudiante estudiante,
            @RequestParam(name = "numerosTelefonos") String telefonosRecibidos) {
        
        LOG.info("Telefonos recibidos: " + telefonosRecibidos);
        
        //Para guardar el estudiante en base de datos
        estudianteService.save(estudiante);

        List<String> listadoNumerosTelefonos = null;

        //Esto tambien se podria solucionar poniendo telefono como required        
        if(telefonosRecibidos != null) {

        //Todo esto tiene sentido solo si hay algun telefono metido, que si no nos saltaria
        //error
        String[] arrayTelefonos = telefonosRecibidos.split(";");

        //Convertirmos el array en lista
        listadoNumerosTelefonos = Arrays.asList(arrayTelefonos);

        }


        //Aqui la variable declarada a nivel de metodo tenemos que asiganarle un valor, solo
        //A nivel de clase se le asigna automaticamente
        if(listadoNumerosTelefonos != null) {
        //Borrar toddos los telefonos que tenga el estudiante, si hay 
        //que insertar nuevos
            telefonoService.deleteByEstudiante(estudiante);
            listadoNumerosTelefonos.stream().forEach(numero -> {
                Telefono telefonoObject = Telefono
                .builder()
                .numero(numero)
                .estudiante(estudiante)
                .build();

            //Esto es para que se nos guarde en mysql sabes, todo relasionado        
            telefonoService.save(telefonoObject);
            });
        }

        return "redirect:/listar"; 
    }

    /**
     * MUESTRA EL FORMULARIO PARA ACTUALIZAR UN ESTUDIANTE
     */
    @GetMapping("/frmActualizar/{id}")
    public String frmActualizarEstudiante(@PathVariable(name = "id") int idEstudiante,
                                Model model) {

        Estudiante estudiante = estudianteService.findById(idEstudiante); 

        List<Telefono> todosTelefonos = telefonoService.findAll();

        List<Telefono> telefonosDelEstudiante = todosTelefonos.stream()
           .filter(telefono -> telefono.getEstudiante().getId() == idEstudiante)
           .collect(Collectors.toList());

        String numerosDeTelefono = telefonosDelEstudiante.stream()
                .map(telefono -> telefono.getNumero())
                .collect(Collectors.joining(";"));

        List<Facultad> facultades = facultadService.findAll();

        model.addAttribute("estudiante", estudiante);
        model.addAttribute("telefonos", numerosDeTelefono);
        model.addAttribute("facultades", facultades);
        
        return "views/formularioAltaEstudiante";
    }

    /**
     * BORRA EL ESTUDIANTE
     */
    @GetMapping("/borrar/{id}")
    public String borrarEstudiante(@PathVariable(name = "id") int idEstudiante) {
        
        estudianteService.delete(estudianteService.findById(idEstudiante));

        return "redirect:/listar"; 
    }

    /**
     * MUESTRA LOS DETALLES
     */
    @GetMapping("/detalles/{id}")
    public String estudianteDetails(@PathVariable(name = "id") int id, Model model) {

        //necesito pedirle al servicio de estudiantes el estudiante que pertenece a ese id
        //para eso necesito el estudiante, nombre y apellido y los telefonos
        Estudiante estudiante = estudianteService.findById(id);
        //Aqui tengo los objetos telefonos, yo solo voy a necesitar los numeros
        List<Telefono> telefonos = telefonoService.findByEstudiante(estudiante);
       
        List<String> numerosTelefono = telefonos.stream()
        .map(t -> t.getNumero())
        //normalmente seria: .collect(Collectors.toList()); pq toList ahora es operacion
        //terminal tambien.
        .toList();

        //Esto sirve para preparar la informacion para que através del motor de plantillas
        //se renderice todo en la vista
        model.addAttribute("telefonos", numerosTelefono);
        model.addAttribute("estudiante", estudiante);
        return "views/detallesEstudiante";
    }
}
