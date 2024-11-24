package com.ecamp.services;

import com.ecamp.model.Alumno;
import com.ecamp.model.Materia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ArchivosServiceImpl implements ArchivosService {
    private List<Alumno> alumnosACargar;
    private final PromedioService promedioServiceImpl;

    /**
     * Constructor que inicializa el servicio de promedio.
     *
     * @param promedioServiceImpl una implementación de {@link PromedioService} utilizada para calcular promedios.
     */
    public ArchivosServiceImpl(PromedioService promedioServiceImpl) {
        this.promedioServiceImpl = promedioServiceImpl;
    }

    /**
     * Exporta los datos de los alumnos y sus materias a un archivo de texto.
     *
     * @param alumnos     un mapa que contiene los alumnos, con su RUT como clave y el objeto {@link Alumno} como valor.
     * @param rutaArchivo la ruta completa del archivo donde se exportarán los datos.
     */


    @Override
    public void exportarDatos(Map<String, Alumno> alumnos, String rutaArchivo) {
        try(BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))){
            for (Alumno alumno : alumnos.values()) {
                escritor.write(String.format("Alumno: %s - %s",
                        alumno.getRut(),
                        alumno.getNombre())


                );

                for (Materia materia :alumno.getMaterias()){
                    escritor.write(String.format("[materia [nombre=%s, notas=[%s]]] ",
                            materia.getNombre(),
                            materia.getNotas()));
                }
                escritor.write("\n");
                for (Materia materia : alumno.getMaterias()) {
                    escritor.write(String.format("Materia : %s - Promedio : %.1f\n",
                            materia.getNombre(),
                            promedioServiceImpl.calcularPromedio(materia.getNotas())
                    ));
                }
                escritor.write("\n");
            }
        }catch (IOException e){
                System.err.println("Error al escribir el archivo: " + e.getMessage());
        }

    }

    /**
     * Genera una carpeta y un archivo de texto en una ubicación específica.
     *
     * Si la carpeta no existe, se crea. Luego, intenta crear un archivo llamado "promedio.txt" dentro de esa carpeta.
     *
     * @param ruta la ruta de la carpeta donde se creará el archivo.
     * @return la ruta completa del archivo creado o un mensaje de error en caso de fallo.
     */

    @Override
    public String Generar_Carpeta_achivo(String ruta) {
        // Crear un objeto File con la ruta de la carpeta
        File carpeta = new File(ruta);
        //Creacion de la carpeta
        if (!carpeta.exists()) {
            if (carpeta.mkdir()) {
                System.out.println("Carpeta recien creada");
            } else {
                System.out.println("Carpeta ya estaba creado");
            }
        }else {
            System.out.println("la carpeta ya existe");
        }

        try {
            File archivo=new File(ruta,"promedio.txt") ;
            // Crear el archivo
            if (archivo.createNewFile()) {
                System.out.println("Archivo creado exitosamente en: " + archivo);
            } else {
                System.out.println("El archivo ya existe en: " + archivo);
            }
            return String.valueOf(archivo);

        } catch (IOException e) {
            System.out.println(" ");
            return "Ocurrió un error al crear el archivo." ;
        }

    }
}


