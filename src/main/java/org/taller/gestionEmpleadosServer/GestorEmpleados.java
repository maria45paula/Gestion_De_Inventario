package org.taller.gestionEmpleadosServer;

import org.taller.Empleado;

import java.util.List;

public class GestorEmpleados implements IGestorEmpleados{
    @Override
    public void agregarEmpleado(List<Empleado> empleados, String credenciales) {
        String[] credencialesSeparadas = credenciales.split(":");
        empleados.add(new Empleado(credencialesSeparadas[0], credencialesSeparadas[1], Integer.parseInt(credencialesSeparadas[2])));
    }

    @Override
    public boolean eliminarEmpleado(String usuario, List<Empleado> empleados) {
        for(Empleado empleado:empleados){
            if(usuario.equals(empleado.getUsuario())){
                empleados.remove(empleado);
                return  true;
            }
        }
        return false;
    }
}
