package com.ecamp.utilidades;

public class Utilidad {
    /**
     * Limpia la pantalla de la consola.
     *
     * Este método detecta el sistema operativo en el que se está ejecutando el programa
     * y ejecuta el comando adecuado para limpiar la consola.
     * En sistemas Windows, utiliza el comando `cls`. En sistemas Unix/Linux/Mac, utiliza `clear`.
     * Si ocurre un error durante el proceso, se muestra un mensaje en la consola.
     */
    public static void limpiarPantalla() {
        try {
            String operatingSystem = System.getProperty("os.name");
            // Check the current operating system
            ProcessBuilder pb;

            if (operatingSystem.contains("Windows")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }

            // Start the process and wait for it to complete
            Process startProcess = pb.inheritIO().start();
            startProcess.waitFor();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    /**
     * Limpia la pantalla de la consola.
     *
     * Este método detecta el sistema operativo en el que se está ejecutando el programa
     * y ejecuta el comando adecuado para limpiar la consola.
     * En sistemas Windows, utiliza el comando `cls`. En sistemas Unix/Linux/Mac, utiliza `clear`.
     * Si ocurre un error durante el proceso, se muestra un mensaje en la consola.
     */

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }


}
