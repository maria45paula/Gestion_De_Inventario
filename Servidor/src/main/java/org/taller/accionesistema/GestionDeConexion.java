package main.java.org.taller.accionesistema;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lleva el registro de todos los clientes (empleados) conectados en
 * este momento, para poder enviarles avisos cuando el inventario cambia,
 * sin que ellos tengan que preguntar primero.
 * <p>
 * Se sincroniza igual que ProductoDAO y AuditLogger: como todos los
 * metodos son synchronized sobre la misma instancia, solo un hilo a la
 * vez puede registrar, eliminar o recorrer la lista de clientes.
 */
public class GestionDeConexion implements IGestorConexion {


    private final List<DataOutputStream> clientesConectados = new ArrayList<>();

    /**
     * Registra a un cliente recién conectado para que reciba avisos.
     *
     * @param salida canal de salida hacia ese cliente.
     */
    public synchronized void registrar(DataOutputStream salida) {
        clientesConectados.add(salida);
    }

    /**
     * Quita a un cliente que se desconectó, para dejar de intentar avisarle.
     *
     * @param salida canal de salida a quitar del registro.
     */
    public synchronized void eliminar(DataOutputStream salida) {
        clientesConectados.remove(salida);
    }

    /**
     * Envía un mensaje a todos los clientes actualmente conectados.
     * Si falla el envío a alguno (por ejemplo, se desconectó justo
     * en ese instante), se ignora ese error puntual y se sigue
     * avisando al resto.
     *
     * @param mensaje texto a enviar (por convención, con el prefijo "NOTIFICACION;").
     */
    public synchronized void broadcast(String mensaje) {
        for (DataOutputStream salida : clientesConectados) {
            try {
                salida.writeUTF(mensaje);
                salida.flush();
            } catch (IOException e) {
                System.out.println("No se pudo avisar a un cliente: " + e.getMessage());
            }
        }
    }
}

