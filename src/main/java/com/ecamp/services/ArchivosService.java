package com.ecamp.services;

import com.ecamp.model.Alumno;

import java.util.Map;

public interface ArchivosService {
    void exportarDatos(Map<String, Alumno> alumnos, String rutaArchivo);
    String Generar_Carpeta_achivo(String ruta);
}
