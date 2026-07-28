package main.java.org.taller.accionesistema;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ExportarCSV {


    public static void crearInventarioCSV(String datos) throws IOException {

        PrintWriter escritor = new PrintWriter(new FileWriter("inventario.csv"));

        escritor.println("Id,Nombre,Categoria,Descripcion,Precio,Cantidad");
        String[] productos = datos.split("~");

        for(String producto : productos){
            if(!producto.isEmpty()){
                escritor.println(producto);
            }
        }
        escritor.close();
    }



    public static void crearAuditoriaCSV(String datos) throws IOException {


        PrintWriter escritor = new PrintWriter(new FileWriter("auditoria.csv"));
        escritor.println("Fecha,Operacion,IP,Recurso");


        String[] acciones = datos.split("~");

        for(String accion : acciones){
            if(!accion.isEmpty()){
                escritor.println(accion);
            }
        }
        escritor.close();

    }
}