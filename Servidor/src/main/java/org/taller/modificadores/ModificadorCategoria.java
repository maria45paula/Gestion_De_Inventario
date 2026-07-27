package main.java.org.taller.modificadores;

import main.java.org.taller.Producto;
import main.java.org.taller.enums.Categoria;


public class ModificadorCategoria implements IModificador {


    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        nuevoDato = nuevoDato.trim().toUpperCase();
        producto.setCategoria(Categoria.valueOf(nuevoDato));

    }
}
