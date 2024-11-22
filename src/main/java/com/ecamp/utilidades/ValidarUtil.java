package com.ecamp.utilidades;

public class ValidarUtil {

    public static String formatRut(String rutSinFormato) {
        try {
            // Validar que el String no sea nulo ni vacío
            if (rutSinFormato == null || rutSinFormato.isEmpty()) {
                throw new IllegalArgumentException("El RUT no puede estar vacío.");
            }

            // Eliminar espacios y caracteres innecesarios
            rutSinFormato = rutSinFormato.trim().replaceAll("[^\\dKk]", "");

            // Validar que el RUT tenga entre 8 y 9 caracteres
            if (rutSinFormato.length() < 8 || rutSinFormato.length() > 12) {
                throw new IllegalArgumentException("El RUT debe tener entre 8 y 12 caracteres.");
            }

            // Validar que el cuerpo tenga solo números y el último carácter sea un dígito o 'K'
            String cuerpo = rutSinFormato.substring(0, rutSinFormato.length() - 1);
            cuerpo.replaceAll("\\.", "");
            String dv = rutSinFormato.substring(rutSinFormato.length() - 1).toUpperCase();

            if (!cuerpo.matches("\\d+")) {
                throw new IllegalArgumentException("El cuerpo del RUT debe contener solo números.");
            }
            if (!dv.matches("\\d|K")) {
                throw new IllegalArgumentException("El dígito verificador debe ser un número o 'K'.");
            }

            // Insertar puntos en el cuerpo del RUT
            StringBuilder cuerpoFormateado = new StringBuilder();
            for (int i = 0; i < cuerpo.length(); i++) {
                if (i > 0 && (cuerpo.length() - i) % 3 == 0) {
                    cuerpoFormateado.append(".");
                }
                cuerpoFormateado.append(cuerpo.charAt(i));
            }

            // Retornar el RUT formateado
            return cuerpoFormateado + "-" + dv;

        } catch (Exception e) {
            // Si ocurre algún error, propagarlo
            throw new IllegalArgumentException("Error al formatear el RUT: " + rutSinFormato, e);
        }
    }

    public static String formatNombre(String nombre) {
        try {
            // Validar que el nombre no sea nulo ni vacío
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }

            // Eliminar espacios adicionales
            nombre = nombre.trim();

            // Validar que el nombre solo contenga letras y espacios
            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                throw new IllegalArgumentException("El nombre solo puede contener letras y espacios.");
            }

            // Formatear el nombre (primera letra en mayúscula de cada palabra)
            String[] palabras = nombre.split("\\s+");
            StringBuilder nombreFormateado = new StringBuilder();

            for (String palabra : palabras) {
                // Convertir la primera letra a mayúscula y el resto a minúscula
                nombreFormateado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1).toLowerCase())
                        .append(" ");
            }

            // Retornar el nombre formateado sin el último espacio
            return nombreFormateado.toString().trim();

        } catch (Exception e) {
            // Propagar el error con un mensaje claro
            throw new IllegalArgumentException("Error al formatear el nombre: " + nombre, e);
        }
    }


    public static String formatApellido(String apellido) {
        try {
            // Validar que el nombre no sea nulo ni vacío
            if (apellido == null || apellido.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }

            // Eliminar espacios adicionales
            apellido = apellido.trim();

            // Validar que el nombre solo contenga letras, espacios y puntos
            if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ .\\-]+")) {
                throw new IllegalArgumentException("El nombre solo puede contener letras, punto y espacios.");
            }

            // Formatear el nombre (primera letra en mayúscula de cada palabra)
            String[] palabras = apellido.split("\\s+");
            StringBuilder apellidoFormateado = new StringBuilder();

            for (String palabra : palabras) {
                // Convertir la primera letra a mayúscula y el resto a minúscula
                apellidoFormateado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1).toLowerCase())
                        .append(" ");
            }

            // Retornar el nombre formateado sin el último espacio
            return apellidoFormateado.toString().trim();

        } catch (Exception e) {
            // Propagar el error con un mensaje claro
            throw new IllegalArgumentException("Error al formatear el nombre: " + apellido, e);
        }
    }

    public static String formatDireccion(String direccion) {
        try {
            // Validar que la dirección no sea nula ni vacía
            if (direccion == null || direccion.trim().isEmpty()) {
                throw new IllegalArgumentException("La dirección no puede estar vacía.");
            }

            // Eliminar espacios adicionales
            direccion = direccion.trim();

            // Validar que la dirección contenga solo caracteres permitidos
            if (!direccion.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ,.#\\-]+")) {
                throw new IllegalArgumentException("La dirección contiene caracteres no permitidos.");
            }

            // Formatear la dirección (primera letra de cada palabra en mayúscula)
            String[] palabras = direccion.split("\\s+");
            StringBuilder direccionFormateada = new StringBuilder();

            for (String palabra : palabras) {
                // Convertir la primera letra a mayúscula, si es alfabética
                if (Character.isLetter(palabra.charAt(0))) {
                    direccionFormateada.append(Character.toUpperCase(palabra.charAt(0)))
                            .append(palabra.substring(1).toLowerCase());
                } else {
                    // Si no es una letra, dejarla tal cual
                    direccionFormateada.append(palabra);
                }
                direccionFormateada.append(" ");
            }

            // Retornar la dirección formateada sin el último espacio
            return direccionFormateada.toString().trim();

        } catch (Exception e) {
            // Propagar el error con un mensaje claro
            throw new IllegalArgumentException("Error al formatear la dirección: " + direccion, e);
        }
    }

}
