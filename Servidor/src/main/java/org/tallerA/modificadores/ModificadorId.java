package main.java.org.tallerA.modificadores;


import main.java.org.tallerA.Producto;

public class ModificadorId implements IModificador {
    @Override
    public void modificarAtributo(Producto producto, String nuevoDato) {
        int nuevoId = Integer.parseInt(nuevoDato);
        producto.setId(nuevoId);
    }
}
