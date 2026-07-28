package main.java.org.tallerA.gestionempleados;

import main.java.org.tallerA.Empleado;

import java.util.ArrayList;
import java.util.List;

public class GestionEmpleado implements IGestorEmpleados {

    /**
     * Guarda la lista de empleados autorizados y valida sus credenciales
     * al momento de iniciar sesión.
     */

    private List<Empleado> empleados = new ArrayList<>();

    /**
     * Crea el gestor con una lista fija de empleados.
     */
    public GestionEmpleado() {
        empleados.add(new Empleado("juan", "clave123"));
        empleados.add(new Empleado("maria", "clave456"));
        empleados.add(new Empleado("Emmanuel", "clave789"));
    }

    public void agregarEmpleado(String usuario,String contrasena) {
        empleados.add(new Empleado(usuario,contrasena));
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

            if (empleado.getUsuario().equals(usuario) && empleado.getContrasena().equals(contrasena)) {
                return true;
            }

        }

        return false;
    }
}

