package com.ecamp.model;

import java.util.HashSet;
import java.util.Set;

public class Alumno {
    private  String rut;
    private  String nombre;
    private  String apellido;
    private  String direccion;
    private Set<Materia> materias;

    public Alumno() {
        this.materias = new HashSet<>();
    }

    public Alumno(String rut, String nombre, String apellido, String direccion) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
    }


    public Alumno(String rut, String nombre, String apellido, String direccion, Set<Materia> materias) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.materias = materias;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(Set<Materia> materias) {
        this.materias = materias ;
    }
    public void agregarMateria(Materia materia){
            materias.add(materia);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Datos Alumno\n");
        sb.append("\tRUT: ").append(rut).append("\n");
        sb.append("\tNombre: ").append(nombre).append("\n");
        sb.append("\tApellido: ").append(apellido).append("\n");
        sb.append("\tDirección: ").append(direccion).append("\n");
        sb.append('\n');
        sb.append("Materias\n");

        if (materias != null && !materias.isEmpty()) {
            for (Materia materia : materias) {
                sb.append('\t').append(materia.getNombre()).append("\n");
                sb.append('\t').append('\t').append("Notas:");
                sb.append( String.format("%s",  materia.getNotas())).append("\n");
            }
        } else {
            sb.append("No tiene materias asignadas\n");
        }

        return sb.toString();
    }
}
