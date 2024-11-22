package com.ecamp.utilidades;

public class Utilidad {
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

    public static void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }


}
