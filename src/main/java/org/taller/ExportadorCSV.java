package org.taller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Genera archivos CSV a partir del inventario y del log de auditoría.
 * Es una clase utilitaria: no guarda estado, solo ofrece métodos estáticos.
 */
public class ExportadorCSV {

    /**
     * Exporta la lista de productos a un archivo CSV.
     *
     * @param productos productos a exportar.
     * @param rutaCsv   ruta del archivo CSV a generar.
     * @throws IOException si falla la escritura del archivo.
     */
    public static void exportarInventario(List<Producto> productos, String rutaCsv) throws IOException {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(rutaCsv))) {
            escritor.println("Id,Nombre,Categoria,Descripcion,Precio,Cantidad");
            for (Producto producto : productos) {
                escritor.println("%d,%s,%s,%s,%d,%d".formatted(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getCategoria(),
                        producto.getDescripcion(),
                        producto.getPrecio(),
                        producto.getCantidad()
                ));
            }
        }
    }

    /**
     * Convierte el archivo de log de auditoría (texto plano) a formato CSV.
     *
     * @param rutaLog ruta del archivo de log generado por AuditLogger.
     * @param rutaCsv ruta del archivo CSV a generar.
     * @throws IOException si falla la lectura o escritura de los archivos.
     */
    public static void exportarAccionesEmpleados(String rutaLog, String rutaCsv) throws IOException {
        try (
                BufferedReader lector = new BufferedReader(new FileReader(rutaLog));
                PrintWriter escritor = new PrintWriter(new FileWriter(rutaCsv))
        ) {
            escritor.println("Fecha,Operacion,IP,Recurso");
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 4) {
                    String fecha = partes[0].trim();
                    String operacion = partes[1].trim();
                    String ip = partes[2].trim().replace("IP: ", "");
                    String recurso = partes[3].trim().replace("Recurso: ", "");
                    escritor.println("%s,%s,%s,%s".formatted(fecha, operacion, ip, recurso));
                }
            }
        }
    }

    /**
     * Lee un archivo de texto completo y lo devuelve como una sola línea,
     * usando "~~" como marcador de salto de línea. Sirve para poder enviar
     * el contenido de un archivo por el protocolo de texto (una sola línea).
     *
     * @param ruta ruta del archivo a leer.
     * @return contenido del archivo con "~~" en lugar de saltos de línea.
     * @throws IOException si falla la lectura del archivo.
     */
    public static String leerArchivoPlano(String ruta) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = lector.readLine()) != null) {
                if (!primeraLinea) {
                    contenido.append("~~");
                }
                contenido.append(linea);
                primeraLinea = false;
            }
        }
        return contenido.toString();
    }
}

