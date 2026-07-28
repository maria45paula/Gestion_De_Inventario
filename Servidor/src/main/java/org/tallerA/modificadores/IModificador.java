package main.java.org.tallerA.modificadores;


import main.java.org.tallerA.Producto;

public interface IModificador {

    /**
     * Método que modifica un atrtibuto específico de un producto
     * @param producto
     * @param nuevoDato
     */
    void modificarAtributo(Producto producto, String nuevoDato);
}
