package main.java.org.taller.conexion;

import java.io.IOException;

public interface IConexionCliente {
    /**
     * Abre la conexión SSL con el servidor y arranca el hilo de escucha.
     *
     * @throws Exception si falla la configuración SSL o la conexión.
     */
    void conectar() throws Exception;

    /**
     * Método getter para el último mensaje enviado por el servidor
     *
     * @return String que contiene el último mensaje del servidor
     */
    String getUltimoMensaje();

    /**
     * Envía una petición al servidor. La respuesta no se devuelve aquí
     * directamente: llega de forma asíncrona y la muestra el hilo de escucha.
     *
     * @param peticion texto de la petición, formato "OPERACION;dato1;dato2;..."
     * @throws IOException si falla el envío.
     */
    void enviarPeticion(String peticion) throws IOException;

    /**
     * Envía una petición al servidor y recibe la respuesta del servidor
     *
     * @param peticion Strign que contiene el texto de la petición, formato "OPERACION;dato1;dato2;..."
     *
     * @return String que contiene el último mensaje mandado por el servidor
     *
     * @throws IOException En caso que la conexion se interrumpa mientras espera el mensaje
     */
    String enviarPeticionYEsperarRespuesta(String peticion) throws IOException;

    /**
     * Cierra la conexión de forma ordenada, avisando al servidor.
     */
    void cerrar();
}
