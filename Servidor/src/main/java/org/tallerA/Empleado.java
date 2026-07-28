package main.java.org.tallerA;

/**
 * Clase que modela un empleado de una empresa
 */
public class Empleado {
    private String usuario;
    private String contrasena;

    /**
     * Constructor por parámetros de la clase
     * @param usuario String usuario del Empleado
     * @param contrasena String constraseña del empleado
     */
    public Empleado(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
