/**
 * Comentario en formato Javadoc
 */

package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L; 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 

    @NotNull (message = "El nombre no puede ser null")
    @Size ( max = 25, min = 4)
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaAlta;
    private LocalDate fechaNacimiento;
    private Genero genero; 
    private double beca; 


    //private int idFacultad; 
    //Esto lo comentamos pq al declarar la columna de union nos lo crea solo

    //Un estudiante tiene una facultad asiq creamos una propiedad facultad
    //ahora para relacionar facultad con estudiante:

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST) 
    //Vamos a indicar la columna que va a tener la relacion de clave externa. Esto es lo q lo crea.
    @JoinColumn(name = "idFacultad")
    private Facultad facultad; 

    //como un estudiante puede tener varios telefonos creamos una lista 
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "estudiante")
    private List<Telefono> telefonos; 
    
    public enum Genero {
        HOMBRE, MUJER, OTRO
    }
    

   
    
}
