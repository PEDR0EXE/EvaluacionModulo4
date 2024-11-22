package com.ecamp.services;

import com.ecamp.model.Alumno;
import com.ecamp.model.Materia;
import com.ecamp.model.MateriaEnum;

import java.util.*;

public class AlumnoServiceImpl implements AlumnoService {
    private static final Map<String, Alumno> listaAlumnos = new HashMap<>();

    static {
        Set<Materia> materiasHortencia = new HashSet<>();
        Set<Materia> materiasCris = new HashSet<>();
        Set<Materia> materiasDevora = new HashSet<>();
        // Crear materias para Juan
        Materia matematicasCris = new Materia(
                MateriaEnum.MATEMATICAS,
                new ArrayList<>(Arrays.asList(5.5, 6.0, 5.8))
        );

        materiasCris.add(matematicasCris);

        Materia lenguajeCris = new Materia(
                MateriaEnum.LENGUAJE,
                new ArrayList<>(Arrays.asList(6.2, 5.9, 6.1))
        );
        materiasCris.add(lenguajeCris);

        // Crear a Juan
        Alumno cris = new Alumno(
                "21.629.852-8",
                "Cris",
                "Portales",
                "Av.Balmaceda 557",
                materiasCris
        );
        // Crear materias para María
        Materia matematicasHotencia = new Materia(
                MateriaEnum.MATEMATICAS,
                new ArrayList<>(Arrays.asList(6.5, 6.8, 7.0))
        );
        materiasHortencia.add(matematicasHotencia);
        Materia historiaHortencia = new Materia(
                MateriaEnum.HISTORIA,
                new ArrayList<>(Arrays.asList(6.3, 6.0, 6.4))
        );
        materiasHortencia.add(historiaHortencia);
        // Crear a María
        Alumno hortencia = new Alumno(
                "18.546.232-1",
                "Hortencia",
                "Gozo",
                "Calle La ultima 87",
                materiasHortencia
        );

        // Crear materias para Devora
        Materia lenguajeDevora = new Materia(
                MateriaEnum.LENGUAJE,
                new ArrayList<>(Arrays.asList(5.8, 5.5, 6.0))
        );
        materiasDevora.add(lenguajeDevora);

        // Crear a Pedro (solo con una materia inicialmente)

        Alumno devora = new Alumno(
                "18.527.491-7",
                "Devora",
                "Salgado",
                "Pasaje Los Aromos 789",
                materiasDevora
        );


        // Agregar todos los alumnos al Map
        listaAlumnos.put(cris.getRut(), cris);
        listaAlumnos.put(devora.getRut(), devora);
        listaAlumnos.put(hortencia.getRut(),hortencia);
    }



    @Override
    public void crearAlumno(Alumno alumno) {
        listaAlumnos.put(alumno.getRut(), alumno);

    }

    @Override
    public void agregarMateria(String rutAlumno, Materia currentMate) {
        Alumno alumno = listaAlumnos.get(rutAlumno);
        int validar = 0;
        for (Materia materias : alumno.getMaterias()) {
            if (materias.getNombre().equals(currentMate.getNombre())) {
                validar = 1;
                break;
            }
        }
        if (validar > 0) {
            System.out.println("La materia " + currentMate.getNombre() + " ya está registrada.");
            System.out.println("--- ¡Proceso anulado! ---");
        } else {
            alumno.agregarMateria(new Materia(currentMate.getNombre(), new ArrayList<>()));
            System.out.println("Materia " + currentMate.getNombre() + " agregada con éxito.");
            System.out.println("--- ¡Materia agregada! ---");
        }

    }

    @Override
    public List<Materia> materiasPorAlumnos(String rutAlumno) {
        Alumno alumno = listaAlumnos.get(rutAlumno);
        return new ArrayList<>(alumno.getMaterias());

    }

    @Override
    public Map<String, Alumno> listarAlumnos() {
        return listaAlumnos;
    }

    @Override
    public void agregarNota(String rut, Materia materia, double nota) {
        Alumno alumno = listaAlumnos.get(rut);

        for (Materia materias : alumno.getMaterias()) {
            if (materias.getNombre() == materia.getNombre()) {
                System.out.println(nota);
                materias.getNotas();
                materias.agregarNota(nota);
                break;
            }
        }

    }
}
