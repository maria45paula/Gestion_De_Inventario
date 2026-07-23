package org.taller.gestionEmpleadosServer;

import org.taller.Empleado;

import java.util.ArrayList;

public interface ILoginServer {
    boolean tieneAcceso(String credenciales, ArrayList<Empleado> empleados);

    boolean esAdministrador(String contrasena);
}
