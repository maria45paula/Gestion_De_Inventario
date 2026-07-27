package main.java.org.taller.accionesproducto;

import main.java.org.taller.Producto;
import main.java.org.taller.enums.Categoria;
import main.java.org.taller.modificadores.IModificador;

import java.util.List;

public interface IProductoAccionado {
    Producto agregarProducto(String nombre, Categoria categoria, int precio, String descripcion, int cantidad);

    void modificar(Producto producto, IModificador modificador, String nuevoDato);

    boolean eliminarProducto(int id);

    Producto buscarProducto(int id);

    List<Producto> getProductos();
}
