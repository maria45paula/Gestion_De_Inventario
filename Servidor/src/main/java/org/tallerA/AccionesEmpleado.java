package main.java.org.tallerA;

import main.java.org.tallerA.accionesistema.ExportadorCSV;
import main.java.org.tallerA.accionesistema.IGestorConexion;
import main.java.org.tallerA.accionesistema.RegistradorDeAcciones;
import main.java.org.tallerA.accionesproducto.IProductoAccionado;
import main.java.org.tallerA.enums.Categoria;
import main.java.org.tallerA.gestionempleados.IGestorEmpleados;
import main.java.org.tallerA.modificadores.IModificador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
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
    private final IProductoAccionado productoDAO;
    private final RegistradorDeAcciones registrarAccion;
    private final IGestorConexion gestorConexiones;
    private final IGestorEmpleados gestorEmpleados;


    /**
     * @param socket          conexión con el cliente.
     * @param productoDAO     inventario compartido entre todos los clientes.
     * @param registrarAccion registrador de auditoría compartido entre todos los clientes.
     */
    public AccionesEmpleado(Socket socket, IProductoAccionado productoDAO, RegistradorDeAcciones registrarAccion, IGestorConexion gestorConexiones, IGestorEmpleados gestorEmpleados) {
        this.socket = socket;
        this.productoDAO = productoDAO;
        this.registrarAccion = registrarAccion;
        this.gestorConexiones = gestorConexiones;
        this.gestorEmpleados = gestorEmpleados;
    }

    @Override
    public void run() {
        DataOutputStream salida = null;
        try (DataInputStream entrada = new DataInputStream(socket.getInputStream())) {
            salida = new DataOutputStream(socket.getOutputStream());
            gestorConexiones.registrar(salida); // para que reciba notificaciones de otros empleados

            while (true) {
                String peticion;
                try {
                    peticion = entrada.readUTF();
                } catch (EOFException finDeConexion) {
                    break; // el cliente cerro la conexion
                }

                if (peticion.equalsIgnoreCase("SALIR")) {
                    break;
                }

                String respuesta = procesarPeticion(peticion);
                salida.writeUTF(respuesta);
                salida.flush();
            }
        } catch (IOException e) {
            System.out.println("Error con el cliente: " + e.getMessage());
        } finally {
            if (salida != null) {
                gestorConexiones.eliminar(salida);
            }
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
        String[] solicitud = peticion.split(";");
        String operacion = solicitud[0].toUpperCase();

        try {
            return switch (operacion) {
                case "AUTENTICAR" -> autenticar(solicitud);
                case "AGREGAR" -> agregar(solicitud);
                case "BUSCAR" -> buscar(solicitud);
                case "ELIMINAR" -> eliminar(solicitud);
                case "MODIFICAR" -> modificar(solicitud);
                case "LISTAR" -> listar();
                case "EXPORTAR" -> exportarInventario();
                case "EXPORTARLOGS" -> exportarAuditoria();
                case "AGREGAREMPLEADO"-> agregarEmpleado(solicitud);
                default -> "ERROR Operacion desconocida";
            };
        } catch (Exception e) {
            return "ERROR " + e.getMessage();
        }
    }

    /**
     * Valida las credenciales de un empleado.
     * Protocolo esperado: "AUTENTICAR;usuario;contrasena"
     */
    private String autenticar(String[] camposPeticion) {
        if (camposPeticion.length < 3) {
            return "ERROR;Formato invalido, use AUTENTICAR;usuario;contrasena";
        }

        String usuario = camposPeticion[1];
        String contrasena = camposPeticion[2];
        boolean credencialesValidas = gestorEmpleados.autenticar(usuario, contrasena);

        if (credencialesValidas) {
            String ipCliente = socket.getInetAddress().getHostAddress();
            RegistradorDeAcciones.registrar("LOGIN", ipCliente, usuario);
            return "OK";
        }
        return "ERROR;Usuario o contrasena incorrectos";
    }

    private String agregarEmpleado(String[] partes) {
        String usuario = partes[1];
        String contrasena=partes[2];

         gestorEmpleados.agregarEmpleado(usuario,contrasena);

        String ip = socket.getInetAddress().getHostAddress();
        RegistradorDeAcciones.registrar("AGREGAREMPLEADO", ip, usuario);

        return "OK; " + "registrado exitosamente";
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

        return "OK; " + producto;
    }

    private String buscar(String[] partes) {
        int id = Integer.parseInt(partes[1]);
        Producto producto = productoDAO.buscarProducto(id);

        if (producto != null) {
            return "OK;" + producto;
        } else {
            return "ERROR;producto no encontrado";
        }
    }

    private String eliminar(String[] partes) {
        int id = Integer.parseInt(partes[1]);
        boolean eliminado = productoDAO.eliminarProducto(id);

        if (eliminado == true) {
            String ip = socket.getInetAddress().getHostAddress();
            RegistradorDeAcciones.registrar("ELIMINAR", ip, "id " + id);
            return "OK;producto eliminado";
        }
        return "ERROR;Producto no encontrado";
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
            System.out.println(new File("acciones.log").getAbsolutePath());
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
            System.out.println(new File("auditoria.log").getAbsolutePath());

            ExportadorCSV.exportarAccionesEmpleados("acciones.log", "auditoria.csv");
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

        return "OK;"+resultado;
    }

}
