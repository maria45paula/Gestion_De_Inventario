package main.java.org.taller;

import main.java.org.taller.conexion.ConexionCliente;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Punto de entrada del proyecto Empleado (cliente de consola).
 * Lee la configuracion de conexion desde cliente.properties,
 * mantiene una conexion abierta durante toda la sesion, y muestra
 * tanto las respuestas a lo que uno pregunta como los avisos que
 * lleguen del servidor cuando otro empleado hace un cambio.
 * <p>
 * Ejemplos de peticiones que se pueden escribir:
 * AGREGAR;Leche;ALIMENTOS;3500;Leche entera 1L;20
 * BUSCAR;1
 * MODIFICAR;1;PRECIO;4000
 * ELIMINAR;1
 * LISTAR
 * EXPORTAR
 * EXPORTARLOGS
 * salir
 */

public class ConexionSocket{
    public ConexionCliente conectar(){
    Properties config = cargarConfiguracion();

    String host = config.getProperty("host", "localhost");
    int puerto = Integer.parseInt(config.getProperty("puerto", "9090"));
    String rutaTruststore = config.getProperty("truststore", "cliente_truststore.p12");
    String claveTruststore = config.getProperty("clave_truststore", "");

    ConexionCliente conexion = new ConexionCliente(host, puerto, rutaTruststore, claveTruststore);

        try {
        conexion.conectar();
    } catch (Exception e) {
        System.out.println("No se pudo conectar al servidor: " + e.getMessage());
        return conexion;
    }
        conexion.cerrar();
        return conexion;
}

/**
 * Carga cliente.properties desde el classpath. Si no existe,
 * se usan valores por defecto (utiles solo para pruebas locales).
 *
 * @return propiedades de configuracion del cliente.
 */
  private static Properties cargarConfiguracion() {
    Properties propiedades = new Properties();
    try (InputStream entrada = ClientePrincipal.class.getClassLoader().getResourceAsStream("cliente.properties")) {
        if (entrada != null) {
            propiedades.load(entrada);
        } else {
            System.out.println("No se encontro cliente.properties, usando valores por defecto (localhost).");
        }
    } catch (IOException e) {
        System.out.println("Error al leer cliente.properties: " + e.getMessage());
    }
    return propiedades;
}


}
