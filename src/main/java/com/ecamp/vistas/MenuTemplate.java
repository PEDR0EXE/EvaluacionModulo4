package com.ecamp.vistas;



import com.ecamp.utilidades.ScannerSingleton;

import java.util.Scanner;

public abstract class MenuTemplate {
    private final Scanner sc;

    protected MenuTemplate() {
        this.sc = ScannerSingleton.getInstance();
    }


    protected  abstract void exportarDatos();

    protected  abstract void crearAlumno();

    protected  abstract void agregarMateria();

    protected  abstract void agregarNotaPasoUno();

    protected  abstract void listarAlumnos();

    protected  abstract void terminarPrograma();

    public void iniciarMenu() {
        int opcion = -1;

        while (opcion != 5) {
            System.out.println(" ");
            System.out.println("=== MENÚ PRINCIPAL ===");
            System.out.println("1. Crear alumno ");
            System.out.println("2. Listar alumnos ");
            System.out.println("3. Agregar materia");
            System.out.println("4. Agregar nota");
            System.out.println("5. Terminar programa");
            System.out.println("6. Exportar datos ");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1 :
                        System.out.println("");
                        crearAlumno();
                        break;
                    case 2 :
                        System.out.println("");
                        listarAlumnos();
                        break;
                    case 3 :
                        System.out.println("");
                        agregarMateria();
                        break;
                    case 4:
                        System.out.println("");
                        agregarNotaPasoUno();
                        break;

                    case 5:
                        System.out.println("");
                        terminarPrograma();

                        break;

                    case 6 :
                        System.out.println("");
                        exportarDatos();
                        break;

                    default:

                        System.out.println("Opción inválida. Intente nuevamente.");

                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("");
                System.out.println("Entrada inválida. Por favor, ingrese un número.");

            }

        }
    }



}