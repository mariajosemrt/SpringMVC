package com.example.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Para hacer esta clase una entidad y crear una tabla necesitamos @:
@Entity
@Table(name = "facultades")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Facultad implements Serializable{
    private static final long serialVersionUID = 1L; 
    
    //Para que el id sea primary key, not null y autoincremental hay que poner esto:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id; 
    private String nombre; 

    //Como en una facultad puede haber muchos estudiantes pues hacemos una listita
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "facultad")
    private List<Estudiante> estudiantes; 


}
