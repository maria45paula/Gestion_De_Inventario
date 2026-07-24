package org.taller;

import org.taller.servidor.Servidor;

public class MainServidor {

    public static void main(String[] args) {
        int puerto = 5000;
        String rutaKeystore = "servidor.p12";
        String claveKeystore = "claveSegura123";

        Servidor servidor = new Servidor(puerto, rutaKeystore, claveKeystore);
        servidor.iniciar();
    }
}
