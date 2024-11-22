package com.ecamp.services;

import java.util.List;

public class PromedioServicioImp implements PromedioService {


    @Override
    public Double calcularPromedio(List<Double> notas) {
        double sumNotas = 0;
        double promedio = 0;

        for (Double nota : notas) {
            sumNotas += nota;
        }
        if (notas.isEmpty()){
            throw new ArithmeticException("No se puede dividir entre 0");
        }else {
            promedio = sumNotas / notas.size();
        }

        return promedio;
    }
}
