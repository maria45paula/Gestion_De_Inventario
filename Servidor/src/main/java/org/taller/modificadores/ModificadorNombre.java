package org.taller.modificadores;

import org.taller.Producto;

public class ModificadorNombre implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {

        producto.setNombre(nuevoDato);
    }
}
