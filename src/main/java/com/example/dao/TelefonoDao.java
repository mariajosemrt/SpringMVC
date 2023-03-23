package com.example.dao;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entities.Estudiante;
import com.example.entities.Telefono;

@Repository
public interface TelefonoDao extends JpaRepository<Telefono, Integer> {

    // @Query(value = "delete from telefonos where estudiante_id = :idEstudiante", nativeQuery = true)
    // long deleteByEstudiante(@Param("idEstudiante") Integer idEstudiante);

    //A parte de las consultas que ya existen, hay algunas que vamos a tener que crear nosotros
    //mismos.
    // @Query(value = "delete from telefonos where estudiante_id = :?", nativeQuery = true)
    // long deleteByEstudiante(Integer idEstudiante);

    long deleteByEstudiante(Estudiante estudiante); 

    //Esto ha salido de autocompletar findByEstudiante    
    // List<Telefono> findByEstudiante(Estudiante estudiante);
    
    //No estaba inicialmente, porque teníamos el CRUD repository. 
    //Si quieres usar tus propias consultas,//genera la implementación de ese método.
    //se usa long para saber la cantidad de registros que ha borrado.
}
