package main.java.org.tallerA.modificadores;

import main.java.org.tallerA.Producto;
import main.java.org.tallerA.enums.Categoria;


public class ModificadorCategoria implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        nuevoDato = nuevoDato.trim().toUpperCase();
        producto.setCategoria(Categoria.valueOf(nuevoDato));

    }
}
