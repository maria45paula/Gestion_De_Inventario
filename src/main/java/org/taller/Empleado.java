package org.taller;

public class Empleado {
    private String usuario;
    private String contrasena;
    private String ip;

    public Empleado(String usuario, String contrasena, String ip) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.ip = ip;
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

    public int getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
