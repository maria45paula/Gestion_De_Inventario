package org.taller.modificadores;

import org.taller.Producto;

public class ModificadorDescripcion implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {

        producto.setDescripcion(nuevoDato);
    }
}
