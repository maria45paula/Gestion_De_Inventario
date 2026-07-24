package org.taller;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyStore;

/**
 * Encapsula la conexión SSL con el servidor de inventario.
 * Cada llamada a enviarPeticion abre una conexión, envía una línea
 * con la petición, espera una línea de respuesta y cierra la conexión.
 */
public class ConexionCliente {

    private final String host;
    private final int puerto;
    private final String rutaTruststore;
    private final String claveTruststore;

    /**
     * @param host            dirección del servidor.
     * @param puerto          puerto del servidor.
     * @param rutaTruststore  ruta del truststore que contiene el certificado del servidor.
     * @param claveTruststore contraseña del truststore.
     */
    public ConexionCliente(String host, int puerto, String rutaTruststore, String claveTruststore) {
        this.host = host;
        this.puerto = puerto;
        this.rutaTruststore = rutaTruststore;
        this.claveTruststore = claveTruststore;
    }

    /**
     * Envía una petición al servidor y devuelve su respuesta.
     *
     * @param peticion texto de la petición, formato "OPERACION;dato1;dato2;..."
     * @return texto de respuesta del servidor.
     * @throws Exception si falla la conexión SSL o la comunicación.
     */
    public String enviarPeticion(String peticion) throws Exception {
        SSLSocketFactory factory = crearFabricaSSL();

        try (SSLSocket socket = (SSLSocket) factory.createSocket(host, puerto);
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            salida.println(peticion);
            String respuesta = entrada.readLine();
            return respuesta != null ? respuesta : "ERROR Sin respuesta del servidor";
        }
    }

    /**
     * Construye la fábrica de sockets SSL a partir del truststore del cliente,
     * que contiene el certificado público del servidor en el que se confía.
     */
    private SSLSocketFactory crearFabricaSSL() throws Exception {
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream entrada = new FileInputStream(rutaTruststore)) {
            trustStore.load(entrada, claveTruststore.toCharArray());
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        return sslContext.getSocketFactory();
    }
}