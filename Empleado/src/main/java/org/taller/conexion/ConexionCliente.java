package main.java.org.taller.conexion;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

/**
 * Encapsula la conexión SSL persistente con el servidor de inventario.
 * A diferencia de una conexión "de un solo uso", esta se mantiene
 * abierta durante toda la sesión, con un hilo dedicado a escuchar
 * los mensajes que lleguen del servidor en cualquier momento
 * (tanto respuestas a lo que se pidió, como notificaciones de
 * cambios hechos por otros empleados).
 */
public class ConexionCliente implements IConexionCliente {

    private final String host;
    private final int puerto;
    private final String rutaTruststore;
    private final String claveTruststore;
    private String ultimoMensaje;

    private SSLSocket socket;
    private DataOutputStream salida;
    private DataInputStream entrada;


    /**
     * @param host            dirección del servidor.
     * @param puerto          puerto del servidor.
     * @param rutaTruststore  ruta del truststore que contiene el certificado del servidor.
     * @param claveTruststore contraseña del truststore.
     */
    public ConexionCliente(String host, int puerto, String rutaTruststore, String claveTruststore) {
        this.ultimoMensaje = null;
        this.host = host;
        this.puerto = puerto;
        this.rutaTruststore = rutaTruststore;
        this.claveTruststore = claveTruststore;
    }


    /**
     * Abre la conexión SSL con el servidor y arranca el hilo de escucha.
     *
     * @throws Exception si falla la configuración SSL o la conexión.
     */
    public void conectar() throws Exception {
        SSLSocketFactory factory = crearFabricaSSL();
        socket = (SSLSocket) factory.createSocket(host, puerto);
        salida = new DataOutputStream(socket.getOutputStream());
        entrada = new DataInputStream(socket.getInputStream());

        Thread hiloEscucha = new Thread(this::escucharMensajes);
        hiloEscucha.setDaemon(true);
        hiloEscucha.start();
    }


    private void mostrarEnConsola(String mensaje) {
        if (mensaje.startsWith("NOTIFICACION;")) {
            System.out.println();
            System.out.println("[Aviso del servidor] " + mensaje.substring("NOTIFICACION;".length()));
            System.out.print("> ");
        } else {
            System.out.println(mensaje.replace("~~", System.lineSeparator()));
            System.out.print("> ");
        }
    }

    /**
     * Recoge los mensajes mandados desde el servidor
     */
    private void escucharMensajes() {
        try {
            while (true) {
                String mensaje = entrada.readUTF();
                synchronized (candado) {
                    ultimoMensaje = mensaje;
                    candado.notifyAll();
                }
                mostrarEnConsola(mensaje);
            }
        } catch (EOFException e) {
            mostrarEnConsola("El servidor cerró la conexión.");
        } catch (IOException e) {
            mostrarEnConsola("Se perdió la conexión con el servidor: " + e.getMessage());
        }
    }

    private void avisarDesconexion(String motivo) {
        mostrarEnConsola("ERROR; " + motivo);
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    /**
     * Envía una petición al servidor. La respuesta no se devuelve aquí
     * directamente: llega de forma asíncrona y la muestra el hilo de escucha.
     *
     * @param peticion texto de la petición, formato "OPERACION;dato1;dato2;..."
     * @throws IOException si falla el envío.
     */
    public void enviarPeticion(String peticion) throws IOException {
        salida.writeUTF(peticion);
        salida.flush();
    }

    /**
     * Cierra la conexión de forma ordenada, avisando al servidor.
     */
    public void cerrar() {
        try {
            salida.writeUTF("SALIR");
            salida.flush();
            socket.close();
        } catch (IOException ignorado) {
            // Si ya se cerro por otro motivo, no hay nada mas que hacer aqui.
        }
    }

    /**
     * Construye la fábrica de sockets SSL a partir del truststore del cliente,
     * que contiene el certificado público del servidor en el que se confía.
     */
    private SSLSocketFactory crearFabricaSSL() throws Exception {
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream entradaArchivo = new FileInputStream(rutaTruststore)) {
            trustStore.load(entradaArchivo, claveTruststore.toCharArray());
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        return sslContext.getSocketFactory();
    }

    private final Object candado = new Object();

    public String enviarPeticionYEsperarRespuesta(String peticion) throws IOException {
        synchronized (candado) {
            ultimoMensaje = null;
        }
        enviarPeticion(peticion);
        synchronized (candado) {
            try {
                candado.wait(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrumpido esperando respuesta", e);
            }
            return ultimoMensaje;
        }
    }
}