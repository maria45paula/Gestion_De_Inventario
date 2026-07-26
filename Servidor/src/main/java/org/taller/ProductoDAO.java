package org.taller;

import org.taller.modificadores.IModificador;

import java.util.ArrayList;
import java.util.List;


public class ProductoDAO {

    private final List<Producto> productos = new ArrayList<>();
    private int siguienteId = 1;

    /**
     * Devuelve una copia de la lista completa de productos.
     * Se devuelve una copia no la lista original, para que quien la reciba
     * pueda recorrerla con seguridad, sin riesgo de que otro hilo la
     * modifique al mismo tiempo.
     *
     * @return copia de la lista de productos en el inventario.
     */
    public synchronized List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    /**
     * Agrega un nuevo producto al inventario, generando su id automáticamente.
     *
     * @param nombre      nombre del producto.
     * @param categoria   categoría del producto.
     * @param precio      precio del producto.
     * @param descripcion descripción del producto.
     * @param cantidad    cantidad disponible.
     * @return el producto ya creado, con su id asignado.
     */
    public synchronized Producto agregarProducto(String nombre, Categoria categoria, int precio, String descripcion, int cantidad) {
        Producto producto = new Producto(siguienteId, nombre, categoria, precio, descripcion, cantidad);
        siguienteId++;
        productos.add(producto);
        return producto;
    }

    /**
     * Modifica un atributo de un producto mediante una interfaz
     *
     * @param producto    producto a modificar
     * @param modificador interfaz de modificacion
     * @param nuevoDato   nuevo valor a asignar
     */
    public synchronized void modificar(Producto producto, IModificador modificador, String nuevoDato) {
        modificador.modificarAtributo(producto, nuevoDato);
    }

    /**
     * Elimina un producto del inventario según su id
     *
     * @param id id del producto a eliminar
     * @return true si se pudo eliminar, false si el producto no lo encontro
     */
    public synchronized boolean eliminarProducto(int id) {
        return productos.removeIf(producto -> producto.getId() == id);
    }

    /**
     * Busca un producto por su id.
     *
     * @param id id del producto a buscar.
     * @return un objeto de tipo producto.
     */
    public synchronized Producto buscarProducto(int id) {
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                return producto;
            }
        }
        return null;
    }
}
