package main.java.org.taller;

import java.util.ArrayList;
import java.util.List;

public class GestionEmpleado {

    /**
     * Guarda la lista de empleados autorizados y valida sus credenciales
     * al momento de iniciar sesión.
     */

    private final List<Empleado> empleados = new ArrayList<>();

    /**
     * Crea el gestor con una lista fija de empleados.
     */
    public GestionEmpleado() {
        empleados.add(new Empleado("juan", "clave123"));
        empleados.add(new Empleado("maria", "clave456"));
    }

    /**
     * Verifica si el usuario y la contraseña corresponden a un empleado registrado.
     *
     * @param usuario    Nombre de usuario.
     * @param contrasena Contraseña ingresada.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public synchronized boolean autenticar(String usuario, String contrasena) {

        for (Empleado empleado : empleados) {

            if (empleado.getUsuario().equals(usuario)
                    && empleado.getContrasena().equals(contrasena)) {

                return true;
            }

        }

        return false;
    }
}

