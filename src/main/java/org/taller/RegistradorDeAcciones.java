package org.taller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistradorDeAcciones {


    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String rutaArchivo = "";

    /**
     * @param rutaArchivo ruta del archivo donde se escribirán los logs.
     */
    public RegistradorDeAcciones(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /**
     * Agrega una línea al archivo de auditoría con los datos de la operación.
     *
     * @param operacion operación realizada (ej: "AGREGAR", "ELIMINAR", "ACTUALIZAR").
     * @param ipCliente dirección IP del cliente que ejecutó la operación.
     * @param recurso   producto o dato afectado por la operación (ej: nombre o id).
     */
    public static synchronized void registrar(String operacion, String ipCliente, String recurso) {
        String fecha = LocalDateTime.now().format(FORMATO_FECHA);
        String linea = "%s | %s | IP: %s | Recurso: %s".formatted(fecha, operacion, ipCliente, recurso);

        try (FileWriter escritorArchivo = new FileWriter(rutaArchivo, true);
             PrintWriter escritor = new PrintWriter(escritorArchivo)) {
            escritor.println(linea);
        } catch (IOException e) {
            System.out.println("Error al escribir en el log de auditoria: " + e.getMessage());
        }
    }
}

