package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Estudiante;

//Dice a spring que busque los beans(se generan) para todos esos metodos que vamos a utilizar
//de JpaRepository
@Repository
public interface EstudianteDao extends JpaRepository<Estudiante, Integer> {
    
}
