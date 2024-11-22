package com.ecamp.vistas;

import com.ecamp.model.Alumno;
import com.ecamp.model.Materia;
import com.ecamp.model.MateriaEnum;
import com.ecamp.services.AlumnoServiceImpl;
import com.ecamp.services.ArchivosServiceImpl;
import com.ecamp.services.PromedioServicioImp;
import com.ecamp.utilidades.Utilidad;
import com.ecamp.utilidades.ValidarUtil;
import com.ecamp.utilidades.ScannerSingleton;

import java.io.File;
import java.util.*;

public class Menu extends MenuTemplate {

    private final String rutaDocumento = "./recurso";
    private final AlumnoServiceImpl alumnoService = new AlumnoServiceImpl();
    private final PromedioServicioImp promedioImp = new PromedioServicioImp();
    private final ArchivosServiceImpl archivoServicio = new ArchivosServiceImpl(promedioImp);
    Map<String, Alumno> listaAlumnos = alumnoService.listarAlumnos();

    public Menu() {
    }


    @Override
    protected void crearAlumno() {
        Utilidad.limpiarPantalla();
        Scanner sc = ScannerSingleton.getInstance();
        Alumno alumno = new Alumno();
        boolean validar = true;
        String rut = "";
        String rutFormateado = "";
        String nombreFormateado = "";
        String apellidoFormateado = "";
        String direccionFormateado = "";
        Utilidad.mostrarMensaje("--- Crear Alumno ---");
        while (validar) {
            List<String> errores = new ArrayList<>();

            System.out.print(" Ingresa RUT: ");
            rut = sc.nextLine().trim();
            try {
                //Usar el método estático desde ValidarUtil
                rutFormateado = ValidarUtil.formatRut(rut);

            } catch (IllegalArgumentException e) {
                // Agregar a la lista de errores
                errores.add(e.getMessage());
            }
            System.out.print(" Ingresa nombre: ");
            alumno.setNombre(sc.nextLine().trim());
            try {
                nombreFormateado = ValidarUtil.formatNombre(alumno.getNombre());
            } catch (IllegalArgumentException e) {
                errores.add(e.getMessage());
            }

            System.out.print(" Ingresa apellido: ");
            alumno.setApellido(sc.nextLine().trim());
            try {
                apellidoFormateado = ValidarUtil.formatApellido(alumno.getApellido());
            } catch (IllegalArgumentException e) {
                errores.add(e.getMessage());
            }
            System.out.print(" Ingresa dirección: ");
            alumno.setDireccion(sc.nextLine());
            try {
                direccionFormateado = ValidarUtil.formatDireccion(alumno.getDireccion());
            } catch (IllegalArgumentException e) {
                errores.add(e.getMessage());
            }

            if (errores.isEmpty()) {
                alumno.setRut(rutFormateado);
                alumno.setNombre(nombreFormateado);
                alumno.setApellido(apellidoFormateado);
                alumno.setDireccion(direccionFormateado);
                alumnoService.crearAlumno(alumno);
                Utilidad.mostrarMensaje(" --- ¡Alumno agregado! ---");
                validar = false;
            } else {
                Utilidad.mostrarMensaje(" --- ERRORES ENCONTRADOS EN EL INGRESO ---");
                for (String error : errores) {
                    System.out.println(error);
                }
                Utilidad.mostrarMensaje("  ---  FIN DE ERRORES  ---");
                Utilidad.mostrarMensaje("   --- POR FAVOR INGRESE NUEVAMENTE ---");
            }


        }
        System.out.println(" ");
    }

    @Override
    protected void listarAlumnos() {
        Utilidad.limpiarPantalla();
        Utilidad.mostrarMensaje(" --- Listar nombres ----");
        for (Map.Entry<String, Alumno> alumnos : listaAlumnos.entrySet()) {
            // Formatear y alinear
            System.out.println();
            System.out.print(alumnos.getValue().toString());
            System.out.println();
        }
        System.out.println(" ");
    }

    @Override
    protected void agregarMateria() {
        Utilidad.limpiarPantalla();
        Scanner sc = ScannerSingleton.getInstance();
        // Convertir las entradas del Map en un arreglo
        int noecontrados = 0;
        boolean materiatrue = true;
        String rut = "";
        int mSeleccionada = 0;
        List<MateriaEnum> materias = new ArrayList<>(Arrays.asList(MateriaEnum.values()));
        String rutFormado = "";
        /*Fin de las variables*/
        Utilidad.mostrarMensaje("---  Agregar Materia ---");
        System.out.print("ingresar rut del Alumno: ");
        rut = sc.nextLine().trim();
        try {
            rutFormado = ValidarUtil.formatRut(rut);
            for (Map.Entry<String, Alumno> alumnosAlmacenado : listaAlumnos.entrySet()) {
                //validar que exista el alumno con el rut registrado como clave primaria
                if (rutFormado.equalsIgnoreCase(alumnosAlmacenado.getKey())) {
                    while (materiatrue) {
                        String materiaSelecionada = "";
                        int numMateria = 0;
                        //mostrar todas las materias existentes
                        for (MateriaEnum materia : materias) {
                            System.out.println(++numMateria + ".- " + materia);
                        }
                        numMateria = 0;
                        Utilidad.mostrarMensaje("seleccione una materia: ");
                        //elijes el numero de la materia
                        materiaSelecionada = sc.nextLine().trim();
                        try {
                            // pasa de cadena de texto a numero
                            mSeleccionada = Integer.parseInt(materiaSelecionada);
                        } catch (Exception e) {
                            //si escribe en letras entrega este mensaje
                            System.out.println("Si escribiste con letras es con solo numeros");
                        }
                        for (int i = 0; i < materias.size(); i++) {
                            // obtienes el nombre de la materia a traves del indice;
                            if (mSeleccionada - 1 == i) {

                                MateriaEnum materiaSeleccionado = materias.get(i);
                                Materia m = new Materia();
                                m.setNombre(materiaSeleccionado);
                                // no se puede repetir la materias ya que el medoto revisa todo el contenido del alumno
                                alumnoService.agregarMateria(rutFormado, m);
                                materiatrue = false;

                            } else {
                                ++numMateria;
                            }


                        }
                        //si elijes la un numero que no esta en el sistema vuelves a proceso de elejir materia
                        if (numMateria == materias.size()) {
                            System.out.println("materia no encontrada");
                        }


                    }
                } else {
                    ++noecontrados;
                }

            }

            if (noecontrados == listaAlumnos.size()) {
                System.out.println("no se ha encontrado el rut en el sistema");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(" ");
    }

    @Override
    protected void agregarNotaPasoUno() {
        Utilidad.limpiarPantalla();
        Scanner sc = ScannerSingleton.getInstance();
        String rut = "";
        String rutFormado = "";
        int noecontrados = 0;
        double nota=0;
        boolean validar=true;
        int m=0;
        Utilidad.mostrarMensaje("--- Agregar nota ---");
        System.out.print("Ingresa rut del Alumno: ");
        rut = sc.nextLine().trim();
        try {
            rutFormado = ValidarUtil.formatRut(rut);
            for (Map.Entry<String, Alumno> alumnosAlmacenado : listaAlumnos.entrySet()) {
                if (rutFormado.equalsIgnoreCase(alumnosAlmacenado.getKey())) {
                    Alumno alumno= alumnosAlmacenado.getValue();
                    List<Materia>alumnoMateria= alumno.getMaterias().stream().toList();
                    System.out.println("Alumno tiene las siguientes materias agregadas:");
                    for (int i = 0; i < alumnoMateria.size(); i++){
                        System.out.println(i+1+" "+ alumnoMateria.get(i).getNombre());
                    }
                   while (validar) {
                       try {
                           System.out.print("Seleccionar materia: ");
                           m = Integer.parseInt(sc.nextLine().trim());
                           validar=false;
                       } catch (Exception e) {
                           System.out.println("Solo numeros");
                       }

                   }
                   validar=true;
                   while (validar){
                       try {
                           for (int i = 0; i < alumnoMateria.size(); i++) {
                               if (m - 1 == i) {
                                   Materia materia = alumnoMateria.get(i);
                                   System.out.println("ingrese nota");
                                   nota = Double.parseDouble(sc.nextLine());
                                   if (nota>=1.0 && nota<=7.0) {
                                       materia.agregarNota(nota);
                                       validar = false;
                                   }else {
                                       System.out.println("por favor ingrese una nota entre 1 hasta el 7 incluidos con decimales ");
                                   }
                               }
                           }
                       } catch (Exception e) {
                           System.out.println("ingrese solo numeros con puntos");
                       }
                   }
                }else {
                    ++noecontrados;
                }
                if (noecontrados==listaAlumnos.size()){
                    System.out.println("--- Alumno no Encontrado ---");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(" ");


    }

    @Override
    protected void terminarPrograma() {
    Utilidad.limpiarPantalla();
    Utilidad.mostrarMensaje("Cerrando Programa.....  ");
    Utilidad.limpiarPantalla();
    }

    @Override
    protected void exportarDatos() {
        String rutaArchivo="";
        rutaArchivo=archivoServicio.Generar_Carpeta_achivo(rutaDocumento);
        if (!rutaArchivo.equalsIgnoreCase("\"Ocurrió un error al crear el archivo.")) {
            try {
                Utilidad.limpiarPantalla();
                Utilidad.mostrarMensaje("=== Exportar Datos ===");


                // VERIFICAR SI HAY ALUMNOS EN LA LISTA
                if (alumnoService.listarAlumnos().isEmpty()) {
                    System.out.println("No hay alumnos registrados para exportar");
                    return;
                }

                // EXPORTAR DATOS USANDO LA RUTA FIJA!!!!!!
                archivoServicio.exportarDatos(alumnoService.listarAlumnos(), rutaArchivo);
                System.out.println("Los datos han sido exportados exitosamente a: " + rutaArchivo);
            } catch (Exception e) {
                System.out.println("Error al exportar los datos: " + e.getMessage());
            }
        }else {
            System.out.println(rutaArchivo);
        }
    }

}
