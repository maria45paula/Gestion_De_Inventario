package main.java.org.tallerA.modificadores;

import main.java.org.tallerA.Producto;

public class ModificadorNombre implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {

        producto.setNombre(nuevoDato);
    }
}
