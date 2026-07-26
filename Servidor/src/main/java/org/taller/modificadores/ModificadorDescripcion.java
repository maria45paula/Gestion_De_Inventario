package main.java.org.taller.modificadores;


import main.java.org.taller.Producto;

public class ModificadorDescripcion implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {

        producto.setDescripcion(nuevoDato);
    }
}
