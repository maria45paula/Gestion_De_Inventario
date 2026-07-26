package main.java.org.taller.modificadores;


import main.java.org.taller.Producto;

public class ModificadorId implements IModificador {
    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        int nuevoId = Integer.parseInt(nuevoDato);
        producto.setId(nuevoId);
    }
}
