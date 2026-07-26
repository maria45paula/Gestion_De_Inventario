package main.java.org.taller.modificadores;


import main.java.org.taller.Producto;

public class ModificadorPrecio implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        int nuevoPrecio = Integer.parseInt(nuevoDato);
        producto.setPrecio(nuevoPrecio);
    }
}
