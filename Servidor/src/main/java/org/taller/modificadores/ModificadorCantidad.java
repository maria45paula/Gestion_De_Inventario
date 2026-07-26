package main.java.org.taller.modificadores;


import main.java.org.taller.Producto;

public class ModificadorCantidad implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        int nuevaCantidad = Integer.parseInt(nuevoDato);
        producto.setCantidad(nuevaCantidad);
    }
}
