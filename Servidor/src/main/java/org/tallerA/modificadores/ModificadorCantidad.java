package main.java.org.tallerA.modificadores;


import main.java.org.tallerA.Producto;

public class ModificadorCantidad implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        int nuevaCantidad = Integer.parseInt(nuevoDato);
        producto.setCantidad(nuevaCantidad);
    }
}
