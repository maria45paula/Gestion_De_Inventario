package main.java.org.tallerA.gestionempleados;

public interface IGestorEmpleados {
    boolean autenticar(String usuario, String contrasena);

    void agregarEmpleado(String usuario, String contrasena);
}
