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
        //Creando materias para Cris
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
        //Creando a Cris
        Alumno cris = new Alumno(
                "21.629.852-8",
                "Cris",
                "Portales",
                "Av.Balmaceda 557",
                materiasCris
        );
        //Creando Materias para hortencia
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

        //Creando a Hortencia
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

        // Creando a Devora
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

    /**
     * Crea un nuevo alumno y lo agrega al sistema si no existe un alumno con el mismo RUT.
     *
     * Este método verifica si el RUT del nuevo alumno ya está registrado en el sistema. Si el RUT ya existe, se muestra un mensaje y no se agrega al alumno. Si el RUT es único, el alumno se agrega a la lista de alumnos.
     *
     * @param alumno el objeto {@link Alumno} que se desea agregar al sistema.
     */

    @Override
    public void crearAlumno(Alumno alumno) {
        List<Alumno>Lalumnos = new ArrayList<>(listaAlumnos.values());
        for (Alumno alumnos: Lalumnos){
            if (alumnos.getRut().equalsIgnoreCase(alumno.getRut())){
                System.out.println("Ese rut ya esta ingresado con el alumno ingrese otro");
                return;
            }

        }
        listaAlumnos.put(alumno.getRut(), alumno);
    }

    /**
     * Agrega una nueva materia a un alumno si aún no tiene esa materia registrada.
     *
     * Este método verifica si el alumno ya tiene registrada la materia especificada. Si la materia ya está registrada, muestra un mensaje indicando que la materia ya está presente y anula el proceso. Si la materia no está registrada, la agrega al alumno y muestra un mensaje de éxito.
     *
     * @param rutAlumno el RUT del alumno al cual se le desea agregar la materia.
     * @param currentMate el objeto {@link Materia} que se desea agregar al alumno.
     */
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
    /**
     * Obtiene la lista de materias de un alumno basado en su RUT.
     *
     * Este método busca el alumno usando el RUT proporcionado y retorna una lista con las materias que el alumno tiene registradas.
     *
     * @param rutAlumno el RUT del alumno cuya lista de materias se desea obtener.
     * @return una lista de objetos {@link Materia} que representan las materias del alumno.
     */
    @Override
    public List<Materia> materiasPorAlumnos(String rutAlumno) {
        Alumno alumno = listaAlumnos.get(rutAlumno);
        return new ArrayList<>(alumno.getMaterias());

    }
    /**
     * Lista todos los alumnos registrados en el sistema.
     *
     * Este método retorna un mapa con los alumnos registrados, donde la clave es el RUT del alumno y el valor es el objeto {@link Alumno} correspondiente.
     *
     * @return un mapa de alumnos con el RUT como clave y el objeto {@link Alumno} como valor.
     */

    @Override
    public Map<String, Alumno> listarAlumnos() {
        return listaAlumnos;
    }

    /**
     * Agrega una nota a una materia específica de un alumno.
     *
     * Este método busca al alumno usando su RUT y la materia especificada. Si el alumno tiene esa materia registrada, agrega la nota proporcionada a la lista de notas de la materia.
     *
     * @param rut el RUT del alumno al que se le va a agregar la nota.
     * @param materia el objeto {@link Materia} en la cual se va a agregar la nota.
     * @param nota el valor de la nota a agregar.
     */

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
