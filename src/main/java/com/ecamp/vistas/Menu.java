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

    /**
     * Método protegido que permite crear un nuevo alumno en el sistema con validaciones para cada campo.
     * <p>
     * Este método guía al usuario a través de un flujo interactivo donde se solicitan los datos del alumno:
     * RUT, nombre, apellido y dirección. Cada entrada es validada mediante utilidades específicas para
     * asegurar que cumpla con los formatos requeridos. Si todos los datos son válidos, el alumno se guarda
     * automáticamente en el sistema. En caso de errores, se muestran mensajes descriptivos y se permite
     * reingresar la información.
     * </p>
     * <p>
     * Este método depende de las siguientes clases y servicios:
     * </p>
     * <ul>
     *     <li>{@code Utilidad}: Para limpiar la pantalla y mostrar mensajes informativos.</li>
     *     <li>{@code ScannerSingleton}: Para obtener una instancia única del escáner para entradas del usuario.</li>
     *     <li>{@code ValidarUtil}: Para validar y formatear los datos ingresados.</li>
     *     <li>{@code alumnoService}: Para almacenar el nuevo alumno en el sistema.</li>
     *
     * </ul>
     *
     * <ul>
     *     <h1> funciones de rutFormateado </h1>
     *     <li>Elimina espacios y caracteres innecesarios del RUT.</li>
     *     <li>Valida que el RUT tenga entre 8 y 12 caracteres.</li>
     *     <li>Verifica que el cuerpo del RUT contenga solo números.</li>
     *     <li>Valida que el dígito verificador sea un número o la letra 'K' (mayúscula o minúscula).</li>
     *     <li>Formatea el cuerpo del RUT insertando puntos como separadores de miles.</li>
     *     <li> String rutFormateado = formatRut("12345678K"); // Retorna "12.345.678-K"</li>
     * </ul>
     *
     *
     * </p>
     *
     * @throws IllegalArgumentException Si alguno de los campos no cumple con las validaciones definidas.
     * Se agrega el alumno con sus validacion cada variable del objecto alumno
     * tiene su contenido de validacion  incluido que el rut tiene su validaciones en donde tiene su
     * formato no es nesesario ingresar el rut con punto y guiones en caso de errores se mostrara po consola
     */

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
            //Si no hay errores se guarda de manera autmatica
            if (errores.isEmpty()) {
                alumno.setRut(rutFormateado);
                alumno.setNombre(nombreFormateado);
                alumno.setApellido(apellidoFormateado);
                alumno.setDireccion(direccionFormateado);
                alumnoService.crearAlumno(alumno);
                Utilidad.mostrarMensaje(" --- ¡Alumno agregado! ---");
                validar = false;
            } else {
                // si hay errores se muestra por consola
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

    /**
     * "Método protegido que lista los alumnos almacenados en el mapa listaAlumnos
     * <p>
     * Si no hay alumnos en la lista, muestra un mensaje informativo y finaliza la ejecución del método.
     * Si hay alumnos, limpia la pantalla, muestra un encabezado, e imprime los detalles de cada alumno
     * en la consola.
     * </p>
     * <p>
     * Este método depende de la clase {@code Utilidad} para mostrar mensajes y limpiar la pantalla.
     * </p>
     *
     * @throws NullPointerException si `listaAlumnos` es {@code null}.
     */

    @Override
    protected void listarAlumnos() {
        if (listaAlumnos.isEmpty()) {
            Utilidad.mostrarMensaje("no hay estudiantes ingrese al menos 1 estudiante ");
            return;
        }
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

    /**
     * Método protegido que permite agregar una materia a un alumno existente en el sistema.
     * <p>
     * Este método verifica si hay alumnos registrados antes de proceder. Si no hay alumnos, muestra
     * un mensaje de error y termina su ejecución. Luego, solicita al usuario ingresar el RUT del alumno,
     * valida su existencia en el sistema y permite seleccionar una materia de una lista predefinida.
     * La materia seleccionada se agrega al alumno si aún no ha sido registrada.
     * </p>
     * <p>
     * Este método depende de varias clases de utilidad, como:
     * <ul>
     *     <li>{@code Utilidad}: Para limpiar la pantalla y mostrar mensajes.</li>
     *     <li>{@code ScannerSingleton}: Para obtener una instancia única del escáner.</li>
     *     <li>{@code ValidarUtil}: Para validar y formatear el RUT del alumno.</li>
     *     <li>{@code alumnoService}: Para realizar operaciones sobre el alumno, como agregar materias.</li>
     * </ul>
     * </p>
     *
     * @throws IllegalArgumentException Si el formato del RUT ingresado no es válido.
     */

    @Override
    protected void agregarMateria() {

        Utilidad.limpiarPantalla();
        if (listaAlumnos.isEmpty()) {
            Utilidad.mostrarMensaje("no hay estudiantes ingrese al menos 1 estudiante ");
            return;
        }
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

    /**
     * Método protegido que permite agregar una nota a una materia específica de un alumno.
     * <p>
     * Este método realiza las siguientes funciones:
     * <ul>
     *     <li>Verifica si existen alumnos registrados; si no, muestra un mensaje y termina la ejecución.</li>
     *     <li>Solicita al usuario el RUT del alumno y valida su formato utilizando {@code ValidarUtil}.</li>
     *     <li>Busca al alumno en la lista; si no se encuentra, muestra un mensaje de error.</li>
     *     <li>Enumera las materias del alumno y permite seleccionar una para agregar una nota.</li>
     *     <li>Valida que la nota ingresada esté en el rango permitido (1.0 a 7.0) y la registra en la materia seleccionada.</li>
     * </ul>
     * </p>
     * <p>
     * Este método depende de las siguientes clases y servicios:
     * <ul>
     *     <li>{@code Utilidad}: Para limpiar la pantalla y mostrar mensajes informativos.</li>
     *     <li>{@code ScannerSingleton}: Para obtener una instancia única del escáner para entradas del usuario.</li>
     *     <li>{@code ValidarUtil}: Para validar y formatear el RUT ingresado.</li>
     *     <li>{@code Materia}: Para almacenar la nota en la materia correspondiente.</li>
     * </ul>
     * </p>
     * <p>
     * Validaciones incluidas:
     * <ul>
     *     <li>La lista de alumnos no debe estar vacía.</li>
     *     <li>El RUT ingresado debe ser válido y existir en el sistema.</li>
     *     <li>La materia seleccionada debe ser válida (número dentro del rango).</li>
     *     <li>La nota debe ser un número decimal entre 1.0 y 7.0.</li>
     * </ul>
     * </p>
     *
     * @throws IllegalArgumentException Si el formato del RUT ingresado no es válido.
     * @throws NumberFormatException Si los valores ingresados para seleccionar una materia o nota no son numéricos.
     */

    @Override
    protected void agregarNotaPasoUno() {
        if (listaAlumnos.isEmpty()) {
            Utilidad.mostrarMensaje("no hay estudiantes ingrese al menos 1 estudiante ");
            return;
        }

        Utilidad.limpiarPantalla();
        Scanner sc = ScannerSingleton.getInstance();
        String rut = "";
        String rutFormado = "";
        int noecontrados = 0;
        double nota = 0;
        boolean validar = true;
        int m = 0;
        Utilidad.mostrarMensaje("--- Agregar nota ---");
        System.out.print("Ingresa rut del Alumno: ");
        rut = sc.nextLine().trim();
        try {
            rutFormado = ValidarUtil.formatRut(rut);
            for (Map.Entry<String, Alumno> alumnosAlmacenado : listaAlumnos.entrySet()) {
                if (rutFormado.equalsIgnoreCase(alumnosAlmacenado.getKey())) {
                    Alumno alumno = alumnosAlmacenado.getValue();
                    List<Materia> alumnoMateria = alumno.getMaterias().stream().toList();
                    System.out.println("Alumno tiene las siguientes materias agregadas:");
                    for (int i = 0; i < alumnoMateria.size(); i++) {
                        System.out.println(i + 1 + " " + alumnoMateria.get(i).getNombre());
                    }
                    while (validar) {
                        try {
                            System.out.print("Seleccionar materia: ");
                            m = Integer.parseInt(sc.nextLine().trim());
                            validar = false;
                        } catch (Exception e) {
                            System.out.println("Solo numeros");
                        }

                    }
                    validar = true;
                    while (validar) {
                        try {
                            for (int i = 0; i < alumnoMateria.size(); i++) {
                                if (m - 1 == i) {
                                    Materia materia = alumnoMateria.get(i);
                                    System.out.println("ingrese nota");
                                    nota = Double.parseDouble(sc.nextLine());
                                    if (nota >= 1.0 && nota <= 7.0) {
                                        materia.agregarNota(nota);
                                        validar = false;
                                    } else {
                                        System.out.println("por favor ingrese una nota entre 1 hasta el 7 incluidos con decimales ");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("ingrese solo numeros con puntos");
                        }
                    }
                } else {
                    ++noecontrados;
                }
                if (noecontrados == listaAlumnos.size()) {
                    System.out.println("--- Alumno no Encontrado ---");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(" ");


    }

    /**
     * Finaliza la ejecución del programa de manera ordenada.
     * <p>
     * Este método realiza las siguientes acciones:
     * <ul>
     *     <li>Limpia la pantalla para proporcionar una experiencia visual ordenada antes de la salida.</li>
     *     <li>Muestra un mensaje de despedida indicando el cierre del programa.</li>
     *     <li>Limpia nuevamente la pantalla para finalizar con un estado limpio.</li>
     * </ul>
     * </p>
     * <p>
     * Dependencias:
     * <ul>
     *     <li>{@code Utilidad}: Utilizado para manejar la limpieza de pantalla y mostrar mensajes al usuario.</li>
     * </ul>
     * </p>
     */

    @Override
    protected void terminarPrograma() {
        Utilidad.limpiarPantalla();
        Utilidad.mostrarMensaje("Cerrando Programa.....  ");
        Utilidad.limpiarPantalla();
    }

    /**
     * Exporta los datos de los alumnos registrados a un archivo en una ubicación predeterminada.
     * <p>
     * Este método realiza las siguientes funciones:
     * <ul>
     *     <li>Genera la ruta donde se almacenará el archivo usando el servicio {@code archivoServicio}.</li>
     *     <li>Verifica si hay alumnos registrados para exportar; de lo contrario, muestra un mensaje y finaliza el proceso.</li>
     *     <li>Exporta los datos de los alumnos a la ruta especificada usando el método {@code exportarDatos} del servicio.</li>
     *     <li>Maneja posibles excepciones para asegurar la estabilidad del sistema en caso de errores.</li>
     * </ul>
     * </p>
     * <p>
     * Validaciones incluidas:
     * <ul>
     *     <li>Se verifica si la lista de alumnos está vacía antes de proceder con la exportación.</li>
     *     <li>Se asegura que la carpeta y el archivo se generen correctamente antes de intentar escribir en ellos.</li>
     * </ul>
     * </p>
     * <p>
     * Dependencias:
     * <ul>
     *     <li>{@code archivoServicio}: Para generar la carpeta y exportar los datos.</li>
     *     <li>{@code alumnoService}: Para obtener la lista de alumnos registrados.</li>
     *     <li>{@code Utilidad}: Para limpiar la pantalla y mostrar mensajes informativos.</li>
     * </ul>
     * </p>
     *
     * @throws RuntimeException Si ocurre un error general durante el proceso de exportación.
     */
    @Override
    protected void exportarDatos() {
        String rutaArchivo = "";
        rutaArchivo = archivoServicio.Generar_Carpeta_achivo(rutaDocumento);
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
        } else {
            System.out.println(rutaArchivo);
        }
    }

}
