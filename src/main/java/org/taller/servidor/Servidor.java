package org.taller.servidor;


import org.taller.ProductoDAO;
import org.taller.RegistradorDeAcciones;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;

/**
 * Servidor TCP seguro (SSL/TLS) del sistema de inventario.
 * Acepta conexiones cifradas de clientes y crea un hilo por cada uno,
 * de forma que varios clientes puedan trabajar al mismo tiempo.
 */
public class Servidor {

    private final int puerto;
    private final String rutaKeystore;
    private final String claveKeystore;
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final RegistradorDeAcciones registrarAccion = new RegistradorDeAcciones("acciones.log");

    /**
     * Crea un servidor SSL que escuchará en el puerto indicado.
     *
     * @param puerto        puerto TCP donde el servidor esperará conexiones.
     * @param rutaKeystore  ruta del archivo .p12 con el certificado del servidor.
     * @param claveKeystore contraseña del keystore.
     */
    public Servidor(int puerto, String rutaKeystore, String claveKeystore) {
        this.puerto = puerto;
        this.rutaKeystore = rutaKeystore;
        this.claveKeystore = claveKeystore;
    }

    /**
     * Inicia el servidor: abre el socket seguro y queda esperando clientes
     * de forma indefinida, atendiendo cada uno en un hilo separado.
     */
    public void iniciar() {
        try {
            SSLServerSocketFactory factory = crearFabricaSSL();

            try (SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(puerto)) {
                System.out.println("Servidor SSL escuchando en el puerto " + puerto);

                while (true) {
                    Socket clienteSocket = serverSocket.accept();
                    System.out.println("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());

                    org.taller.servidor.AccionesEmpleado accion = new org.taller.servidor.AccionesEmpleado(clienteSocket, productoDAO, registrarAccion);
                    Thread hilo = new Thread(accion);
                    hilo.start();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    /**
     * Construye la fábrica de sockets SSL a partir del keystore del servidor,
     * que contiene su certificado y su clave privada.
     *
     * @return fábrica lista para crear SSLServerSocket.
     * @throws Exception si falla la carga del keystore o la configuración SSL.
     */
    private SSLServerSocketFactory crearFabricaSSL() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream entrada = new FileInputStream(rutaKeystore)) {
            keyStore.load(entrada, claveKeystore.toCharArray());
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, claveKeystore.toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        return sslContext.getServerSocketFactory();
    }
}