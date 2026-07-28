package main.java.org.tallerA.accionesistema;

import java.io.DataOutputStream;

public interface IGestorConexion {
    void registrar(DataOutputStream salida);

    void eliminar(DataOutputStream salida);

    void broadcast(String mensaje);
}
