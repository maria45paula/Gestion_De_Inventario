package org.taller;

import org.taller.modificadores.IModificador;

public class ProductoDAO {

    public void modificar(Producto producto, IModificador modificador, String entrada){
        modificador.modificarAtributo(producto, entrada);
    }

}
