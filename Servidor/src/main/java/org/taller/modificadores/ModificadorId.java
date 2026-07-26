package org.taller.modificadores;

import org.taller.Producto;

public class ModificadorId implements IModificador{
    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        int nuevoId = Integer.parseInt(nuevoDato);
        producto.setId(nuevoId);
    }
}
