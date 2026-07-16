package org.taller.modificadores;

import org.taller.Categoria;
import org.taller.Producto;

public class ModificadorCategoria implements IModificador{


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
         nuevoDato = nuevoDato.trim().toUpperCase();
         producto.setCategoria(Categoria.valueOf(nuevoDato));

    }
}
