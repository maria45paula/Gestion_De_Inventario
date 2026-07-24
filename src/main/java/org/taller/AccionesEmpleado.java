package org.taller.servidor;

import org.taller.Categoria;
import org.taller.ExportadorCSV;
import org.taller.Producto;
import org.taller.ProductoDAO;
import org.taller.RegistradorDeAcciones;
import org.taller.modificadores.IModificador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Atiende la conexión de un único cliente: lee su petición,
 * la interpreta, ejecuta la operación correspondiente sobre
 * el inventario y responde con el resultado.
 * Se ejecuta dentro de su propio hilo (Runnable).
 */
public class AccionesEmpleado implements Runnable {

    private final Socket socket;
    private final ProductoDAO productoDAO;
    private final RegistradorDeAcciones registrarAccion;

    /**
     * @param socket          conexión con el cliente.
     * @param productoDAO     inventario compartido entre todos los clientes.
     * @param registrarAccion registrador de auditoría compartido entre todos los clientes.
     */
    public AccionesEmpleado(Socket socket, ProductoDAO productoDAO, RegistradorDeAcciones registrarAccion) {
        this.socket = socket;
        this.productoDAO = productoDAO;
        this.registrarAccion = registrarAccion;
    }

    @Override
    public void run() {
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String peticion = entrada.readLine();
            if (peticion != null) {
                String respuesta = procesarPeticion(peticion);
                salida.println(respuesta);
            }
        } catch (IOException e) {
            System.out.println("Error con el cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el socket: " + e.getMessage());
            }
        }
    }

    /**
     * Interpreta el texto recibido y ejecuta la operación indicada.
     *
     * @param peticion texto recibido, formato "OPERACION;dato1;dato2;..."
     * @return texto de respuesta, formato "OK;..." o "ERROR;..."
     */
    private String procesarPeticion(String peticion) {
        String[] partes = peticion.split(";");
        String operacion = partes[0].toUpperCase();

        try {
            return switch (operacion) {
                case "AGREGAR" -> agregar(partes);
                case "BUSCAR" -> buscar(partes);
                case "ELIMINAR" -> eliminar(partes);
                case "MODIFICAR" -> modificar(partes);
                case "LISTAR" -> listar();
                case "EXPORTAR" -> exportarInventario();
                case "EXPORTARLOGS" -> exportarAuditoria();
                default -> "ERROR;Operacion desconocida";
            };
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        }
    }

    private String agregar(String[] partes) {
        String nombre = partes[1];
        Categoria categoria = Categoria.valueOf(partes[2].toUpperCase());
        int precio = Integer.parseInt(partes[3]);
        String descripcion = partes[4];
        int cantidad = Integer.parseInt(partes[5]);

        Producto producto = productoDAO.agregarProducto(nombre, categoria, precio, descripcion, cantidad);

        String ip = socket.getInetAddress().getHostAddress();
        RegistradorDeAcciones.registrar("AGREGAR", ip, producto.getNombre());

        return "listo " + producto;
    }

    private String buscar(String[] partes) {
        int id = Integer.parseInt(partes[1]);
        Producto producto = productoDAO.buscarProducto(id);

        if (producto != null) {
            return "se encontro:" + producto;
        } else {
            return "producto no encontrado";
        }
    }

    private String eliminar(String[] partes) {
        int id = Integer.parseInt(partes[1]);
        boolean eliminado = productoDAO.eliminarProducto(id);

        if (eliminado == true) {
            String ip = socket.getInetAddress().getHostAddress();
            RegistradorDeAcciones.registrar("ELIMINAR", ip, "id " + id);
            return "producto eliminado";
        }
        return "producto no encontrado";
    }

    /**
     * Modifica un atributo de un producto existente, reutilizando el patrón
     * Strategy (IModificador) que ya tenías definido: se elige la acción de
     * modificación según el atributo pedido, usando una expresión lambda.
     * Protocolo esperado: "MODIFICAR;id;atributo;nuevoDato"
     */
    private String modificar(String[] partes) {
        int id = Integer.parseInt(partes[1]);
        String atributo = partes[2].toUpperCase();
        String nuevoDato = partes[3];

        Producto producto = productoDAO.buscarProducto(id);

        if (producto == null) {
            return "ERROR;Producto no encontrado";
        }

        IModificador modificador = switch (atributo) {
            case "NOMBRE" -> (p, dato) -> p.setNombre(dato);
            case "PRECIO" -> (p, dato) -> p.setPrecio(Integer.parseInt(dato));
            case "DESCRIPCION" -> (p, dato) -> p.setDescripcion(dato);
            case "CANTIDAD" -> (p, dato) -> p.setCantidad(Integer.parseInt(dato));
            case "CATEGORIA" -> (p, dato) -> p.setCategoria(Categoria.valueOf(dato.toUpperCase()));
            default -> null;
        };

        if (modificador == null) {
            return "ERROR;Atributo desconocido: " + atributo;
        }

        productoDAO.modificar(producto, modificador, nuevoDato);

        String ip = socket.getInetAddress().getHostAddress();
        RegistradorDeAcciones.registrar("ACTUALIZAR", ip, "id " + id + " (" + atributo + ")");

        return "OK;" + producto;
    }

    /**
     * Genera el CSV del inventario en el servidor y lo envía al cliente.
     */
    private String exportarInventario() {
        try {
            List<Producto> productos = productoDAO.getProductos();
            ExportadorCSV.exportarInventario(productos, "inventario.csv");
            String contenido = ExportadorCSV.leerArchivoPlano("inventario.csv");
            return "OK;" + contenido;
        } catch (IOException e) {
            return "ERROR;" + e.getMessage();
        }
    }

    /**
     * Genera el CSV del log de auditoría en el servidor y lo envía al cliente.
     */
    private String exportarAuditoria() {
        try {
            ExportadorCSV.exportarAccionesEmpleados("auditoria.log", "auditoria.csv");
            String contenido = ExportadorCSV.leerArchivoPlano("auditoria.csv");
            return "OK;" + contenido;
        } catch (IOException e) {
            return "ERROR;" + e.getMessage();
        }
    }

    private String listar() {
        List<Producto> productos = productoDAO.getProductos();
        String resultado = "";
        for (Producto producto : productos) {
            resultado += producto + "|";
        }

        return resultado;
    }

}
