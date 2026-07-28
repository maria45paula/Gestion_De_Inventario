package main.java.org.tallerA.modificadores;


import main.java.org.tallerA.Producto;

public class ModificadorPrecio implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        int nuevoPrecio = Integer.parseInt(nuevoDato);
        producto.setPrecio(nuevoPrecio);
    }
}
