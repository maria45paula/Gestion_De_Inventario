package org.taller.gestionEmpleadosServer;

import org.taller.Empleado;

import java.util.List;

public interface IGestorEmpleados {
    void agregarEmpleado(List<Empleado> empleados, String credenciales);

    boolean eliminarEmpleado(String usuario, List<Empleado> empleados);
}
