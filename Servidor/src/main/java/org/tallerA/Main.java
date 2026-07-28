package main.java.org.tallerA;


import main.java.org.tallerA.servidor.IServidor;
import main.java.org.tallerA.servidor.Servidor;

public class Main {

    /**
     * Punto de entrada del servidor de inventario.
     */

    public static void main(String[] args) {
        int puerto = 9999;
        String rutaKeystore = "servidor.p12";
        String claveKeystore = "claveSegura123";

        IServidor servidor = new Servidor(puerto, rutaKeystore, claveKeystore);
        servidor.iniciar();
    }
}

