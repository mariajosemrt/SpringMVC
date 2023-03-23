package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //Hay que añadir transactional en save y delete
    @Override
    @Transactional
    public void save(Estudiante estudiante) {
        estudianteDao.save(estudiante);    
    }

    @Override
    @Transactional
    public void deleteById(int idEstudiante) {
        estudianteDao.deleteById(idEstudiante);
    }

    @Override
    @Transactional
    public void delete(Estudiante estudiante) {
        estudianteDao.delete(estudiante);
    }
    
}
