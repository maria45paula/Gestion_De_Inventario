package main.java.org.taller.modificadores;

import main.java.org.taller.Producto;

public class ModificadorNombre implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {

        producto.setNombre(nuevoDato);
    }
}
