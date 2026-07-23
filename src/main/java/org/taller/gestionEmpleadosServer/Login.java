package org.taller.gestionEmpleadosServer;

import org.taller.Empleado;

import java.util.ArrayList;

public class Login implements ILoginServer{
    @Override
    public boolean tieneAcceso(String credenciales, ArrayList<Empleado> empleados) {
        String[] credencialesSeparadas = credenciales.split(":");
        String usuario = credencialesSeparadas[0];
        String contrasena= credencialesSeparadas[1];
        for(Empleado empleado : empleados) {
            if (usuario.equals(empleado.getUsuario()) && contrasena.equals(empleado.getContrasena()))
                return true;
        }
        return false;

    }

    @Override
    public boolean esAdministrador(String contrasena) {
        if(contrasena.equals("contraseña")) return  true;

        return false;
    }
}
