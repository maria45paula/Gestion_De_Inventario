package org.taller;

import org.taller.modificadores.IModificador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProductoDAO {
    private List<Producto> producto = new ArrayList<>();
    private int id;


    public void agregarProducto() {
        Scanner entrada = new Scanner(System.in);
        Producto producto = new Producto(0, "0", Categoria.ASEO, 0, "0", 0);
        System.out.println("Ingresar nombre del Producto:");
        producto.setNombre(entrada.nextLine());
        System.out.println("Ingresar la categoria del producto:");
        Categoria categoria = Categoria.valueOf(entrada.nextLine().trim().toUpperCase());
        producto.setCategoria(categoria);
        System.out.println("Ingresar el precio del Producto:");
        producto.setPrecio(entrada.nextInt());
        entrada.nextLine();
        System.out.println("Ingresar descripcion del producto:");
        producto.setDescripcion(entrada.nextLine());
        System.out.println("Ingresar la cantidad:");
        producto.setCantidad(entrada.nextInt());
        this.producto.add(producto);
    }

    public void modificar(Producto producto, IModificador modificador, String entrada) {
        modificador.modificarAtributo(producto, entrada);
    }

    public void buscarProducto(int id) {
        for (Producto producto1 : producto) {
            if (producto1.getId() == id) {
                System.out.println("Nombre:" + producto1.getNombre() + "\n Categoria:" + producto1.getCategoria() + "\n Precio:" + producto1.getPrecio()
                        + "\n Descripcion:" + producto1.getDescripcion() + "\n Cantidad:" + producto1.getCantidad());

            } else {
                System.out.println("usuario no encontrado");
            }
        }
    }
}
