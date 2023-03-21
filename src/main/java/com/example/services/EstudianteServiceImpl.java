package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.EstudianteDao;
import com.example.entities.Estudiante;


//La implementacion de servicio tiene que ser anotada con: 
@Service
//Si no no se buscarán los beans de acceso a dato y toda la movida

public class EstudianteServiceImpl implements EstudianteService {

    //Inyecta un objeto del tipo que digamos, en este caso EstudianteDao, una dependencia
    //de la cual depende mi clase 
    @Autowired     
    private EstudianteDao estudianteDao;

    @Override
    public List<Estudiante> findAll() {
        return estudianteDao.findAll(); 
    }

    @Override
    public Estudiante findById(int idEstudiante) {
        return estudianteDao.findById(idEstudiante).get();
    }

    @Override
    public void save(Estudiante estudiante) {
        estudianteDao.save(estudiante);    
    }

    @Override
    public void deleteById(int idEstudiante) {
        estudianteDao.deleteById(idEstudiante);
    }
    
}
