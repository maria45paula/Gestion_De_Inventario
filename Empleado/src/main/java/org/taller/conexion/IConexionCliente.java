package main.java.org.taller.conexion;

import java.io.IOException;

public interface IConexionCliente {
    void conectar() throws Exception;

    String getUltimoMensaje();

    void enviarPeticion(String peticion) throws IOException;

    String enviarPeticionYEsperarRespuesta(String peticion) throws IOException;


    void cerrar();
}
