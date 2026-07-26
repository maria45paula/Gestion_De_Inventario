package org.taller.eliminador;

import org.taller.Producto;

import java.util.ArrayList;
import java.util.Scanner;

public class Eliminador {

    public static void eliminar(int id, ArrayList<Producto> producto) {

        Scanner sc = new Scanner(System.in);
        // se cambia por la entrada del textField
        System.out.println("Ingrese el ID del producto que desea eliminar");
        int IDEliminable = sc.nextInt();
        producto.removeIf(p -> p.getId() == IDEliminable);
        System.out.println("El producto ha sido eliminado correctamente");


    }
}
