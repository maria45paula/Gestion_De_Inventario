package org.taller;

public class Producto {
    private int id;
    private String nombre;
    private Enum categoria;
    private int precio;
    private String descripcion;
    private int cantidad;

    public Producto(int id, String nombre, Enum categoria, int precio, String descripcion, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Enum getCategoria() {
        return categoria;
    }

    public void setCategoria(Enum categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /*
     *Aqui formamos el formato csv, sobreescribiendo el medoto que transforma un objeto en texto
     * */
    @Override
    public String toString() {
        return "%d:%s:%s:%s:%d:%d".formatted(id, nombre, categoria, descripcion, precio, cantidad);
    }
}
