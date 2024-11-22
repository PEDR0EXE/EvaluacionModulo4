package com.ecamp.services;

import com.ecamp.model.Alumno;
import com.ecamp.model.Materia;

import java.util.List;
import java.util.Map;


public interface AlumnoService {
    void crearAlumno(Alumno alumno);
    void agregarMateria(String rutAlumno, Materia currentMate);
    List<Materia> materiasPorAlumnos(String rutAlumno);
    Map<String, Alumno> listarAlumnos();
    void agregarNota(String rut, Materia materia, double nota);
}
