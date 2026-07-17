package org.taller.visualizador;

import org.taller.Producto;
import org.taller.ProductoDAO;

public class Visualizador {

    public void mostrarProductos(ProductoDAO productoDAO) {

        if (productoDAO.getProducto().isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        System.out.println("===== LISTA DE PRODUCTOS =====");

        for (Producto p : productoDAO.getProducto()) {
            System.out.println("ID: " + p.getId());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Categoría: " + p.getCategoria());
            System.out.println("Precio: $" + p.getPrecio());
            System.out.println("Descripción: " + p.getDescripcion());
            System.out.println("Cantidad: " + p.getCantidad());
            System.out.println("--------------------------------");
        }
    }
}
