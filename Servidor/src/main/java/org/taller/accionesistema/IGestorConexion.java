package main.java.org.taller.accionesistema;

import java.io.DataOutputStream;

public interface IGestorConexion {
    void registrar(DataOutputStream salida);

    void eliminar(DataOutputStream salida);

    void broadcast(String mensaje);
}
