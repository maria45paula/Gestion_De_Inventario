package org.taller;

import java.util.Scanner;

public class MainCliente {

    /**
     * Punto de entrada del cliente de inventario (versión de consola,
     * sin interfaz gráfica). Permite escribir peticiones manualmente
     * en el formato del protocolo y ver la respuesta del servidor.
     * <p>
     * Ejemplos de peticiones que se pueden escribir:
     * AGREGAR;Leche;ALIMENTOS;3500;Leche entera 1L;20
     * BUSCAR;1
     * MODIFICAR;1;PRECIO;4000
     * ELIMINAR;1
     * LISTAR
     * EXPORTAR
     * EXPORTARLOGS
     */


    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 5000;
        String rutaTruststore = "cliente_truststore.p12";
        String claveTruststore = "claveClienteSegura123";

        ConexionCliente conexion = new ConexionCliente(host, puerto, rutaTruststore, claveTruststore);
        Scanner teclado = new Scanner(System.in);

        System.out.println("Cliente de inventario (consola). Escribe una peticion o 'salir' para terminar.");

        String peticion;
        while (true) {
            System.out.print("> ");
            peticion = teclado.nextLine();

            if (peticion.equalsIgnoreCase("salir")) {
                break;
            }

            try {
                String respuesta = conexion.enviarPeticion(peticion);
                System.out.println(respuesta.replace("~~", System.lineSeparator()));
            } catch (Exception e) {
                System.out.println("Error de conexion: " + e.getMessage());
            }
        }

        System.out.println("Cliente finalizado.");
    }
}

