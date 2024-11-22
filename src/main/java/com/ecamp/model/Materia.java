package com.ecamp.model;

import java.util.ArrayList;
import java.util.List;

public class Materia {

    private MateriaEnum nombre;
    private List <Double> notas;

    public Materia() {
    }

    public Materia(MateriaEnum nombre, List<Double> notas) {
        this.nombre = nombre;
        this.notas = notas != null ? notas: new ArrayList<>();
    }

    public MateriaEnum getNombre() {
        return nombre;
    }

    public void setNombre(MateriaEnum nombre) {
        this.nombre = nombre;
    }

    public List <Double> getNotas() {
        if (this.notas==null){
            return new ArrayList<>();
        }
            return notas;

    }

    public void setNotas(List<Double> notas) {
        this.notas = notas != null ? notas: new ArrayList<>();
    }

    public void agregarNota (Double nota){
        notas.add(nota);

    }

    @Override
    public String toString() {

        return '\t' +getNombre().toString() +'\n'
                + '\t'+"notas:"+ '['+getNotas()+']'+'\n';
    }
}
