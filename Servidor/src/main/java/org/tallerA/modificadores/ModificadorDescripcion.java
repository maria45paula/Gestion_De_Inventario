package main.java.org.tallerA.modificadores;


import main.java.org.tallerA.Producto;

public class ModificadorDescripcion implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {

        producto.setDescripcion(nuevoDato);
    }
}
