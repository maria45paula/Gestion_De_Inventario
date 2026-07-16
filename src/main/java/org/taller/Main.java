package org.taller;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductoDAO producto1 = new ProductoDAO();
        producto1.agregarProducto();

        System.out.println("Introduzca la contraseña: ");
        String contrasena = scanner.nextLine();
        if(contrasena.equals("12345678")){
            Empleado empleado = new Empleado("Empleado", "12345678", 9876544);

        }else{
            System.out.println("Acceso denegado");
        }


    }
}