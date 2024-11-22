package com.ecamp.services;
import static org.mockito.Mockito.*;

import com.ecamp.model.Alumno;
import com.ecamp.model.Materia;
import com.ecamp.model.MateriaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AlumnoServiceImplTest {
    private AlumnoServiceImpl alumnoServicio;
    private AlumnoServiceImpl alumnoServicioMock;

    private Materia matematicas;
    private Materia lenguaje;
    private Alumno mapu;

    @BeforeEach
    void setup() {
        // Inicializar los objetos
        alumnoServicio = new AlumnoServiceImpl();
        alumnoServicioMock = Mockito.mock(AlumnoServiceImpl.class);
        matematicas = new Materia();
        matematicas.setNombre(MateriaEnum.MATEMATICAS);
        lenguaje = new Materia();
        lenguaje.setNombre(MateriaEnum.LENGUAJE);

        mapu = new Alumno("9.557.658-5","Rosa", "de la Cerda", "la Via #87");
    }

    @Test
    void crearAlumnoTest() {
        alumnoServicio.crearAlumno(mapu);
        Map<String,Alumno> alumnos = alumnoServicio.listarAlumnos();
        assertTrue(alumnos.containsKey(mapu.getRut()), "El alumno debería estar en la lista");
    }

    @Test
    void agregarMateriaTest() {
        lenguaje= new Materia(MateriaEnum.LENGUAJE, new ArrayList<>());
        Set<Materia> materiasmapu = new HashSet<>();
        materiasmapu.add(lenguaje);
        mapu= new Alumno("8.547.678-6","Esteban", "Quemado", "la gula #551", materiasmapu );
        alumnoServicio.crearAlumno(mapu);
        alumnoServicio.agregarMateria(mapu.getRut(), matematicas);

        List<Materia> materias = alumnoServicio.materiasPorAlumnos(mapu.getRut());
        assertTrue(materias.contains(matematicas), "La materia debería haberse agregado al alumno");
    }

    @Test
    void materiasPorAlumnosTest() {
        // Mockear el comportamiento
        when(alumnoServicioMock.materiasPorAlumnos(mapu.getRut()))
                .thenReturn(Arrays.asList(matematicas, lenguaje));

        List<Materia> materias = alumnoServicioMock.materiasPorAlumnos(mapu.getRut());
        assertEquals(2, materias.size(), "El alumno debería tener 2 materias");
        assertTrue(materias.contains(matematicas), "Debe incluir matemáticas");
        assertTrue(materias.contains(lenguaje), "Debe incluir lenguaje");

        // Verificar que se haya llamado el método
        verify(alumnoServicioMock).materiasPorAlumnos(mapu.getRut());
    }

    @Test
    void listarAlumnosTest() {

        // Agregar el alumno al servicio
        alumnoServicio.crearAlumno(mapu);

        // Obtener el mapa de alumnos
        Map<String, Alumno> alumnos = alumnoServicio.listarAlumnos();

        // Verificar que hay un elemento en el mapa
        assertEquals(1, alumnos.size(), "Debería haber un alumno en la lista");

        // Verificar que el mapa contiene al alumno con la clave correcta
        assertTrue(alumnos.containsKey(mapu.getRut()), "El mapa debería contener la clave :" + mapu.getRut());

        // Verificar el nombre del alumno
        assertEquals("Mapu", alumnos.get(mapu.getRut()).getNombre(), "El nombre del alumno debería ser 'Mapu'");
    }
}