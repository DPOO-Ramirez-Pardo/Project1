package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Categoria;
import uniandes.dpoo.proyecto1.modelo.Cliente;
import uniandes.dpoo.proyecto1.modelo.Producto;
import uniandes.dpoo.proyecto1.modelo.Recibo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public abstract class ProcesamientoSupermercado {
    protected HashMap<Integer, Producto> productosPorCodigo;
    protected TreeMap<String, Producto> productosPorNombre;
    protected TreeMap<String, Categoria> categorias;
    protected HashMap<Integer, Cliente> clientesPorCedula;
    protected ManejadorArchivos manejadorArchivos;
    protected ArrayList<Recibo> recibosSinCedula;

    /**
     *
     * @param codigo
     * @return si existe algún producto con ese código se retorna el producto; de lo contrario retorna null.
     */
    public Producto getProductoPorCodigo(int codigo){
        return productosPorCodigo.get(codigo);
    }

    /**
     *
     * @param cedula
     * @return si existe algún cliente con esa cédula se retorna ese cliente; de lo contrario retorna null.
     */
    public Cliente clientePorCedula(int cedula){
        return clientesPorCedula.get(cedula);
    }

    /**
     *
     * @param nombre
     * @return el producto con el nombre correspondiente.
     * @throws Exception si no hay un producto con ese nombre. En caso de haber productos en el catalogo, el mensaje de
     * la excepción devuelve los nombres de los productos con los nombres más cercanos. En el caso contrario, el mensaje
     * es "No hay productos en el catálogo."
     */
    public Producto getProductoPorNombre(String nombre) throws Exception {
        Producto producto = productosPorNombre.get(nombre);
        if (producto != null) return producto;
        else throw nombreNoEncontradoException("productos",
                    productosPorNombre.ceilingKey(nombre), productosPorNombre.floorKey(nombre));
    }

    /**
     *
     * @param nombre
     * @return la categoría con el nombre correspondiente.
     * @throws Exception si no hay un categoría con ese nombre. En caso de haber categorías en el catalogo, el mensaje de
     * la excepción devuelve los nombres de las categorías con los nombres más cercanos. En el caso contrario, el mensaje
     * es "No hay categorías en el catálogo."
     */
    public Categoria getCategoriaPorNombre(String nombre) throws Exception {
        Categoria categoria = categorias.get(nombre);
        if (categoria != null) return categoria;
        else throw nombreNoEncontradoException("categorías",
                    categorias.ceilingKey(nombre), categorias.floorKey(nombre));
    }

    private Exception nombreNoEncontradoException(String elemento, String ceiling, String floor) {
        if (ceiling == null && floor == null) {
            return new Exception("No hay " + elemento + " en el catálogo.");
        } else if (ceiling == null) {
            return new Exception("Nombre no encontrado. Quizá quiciste decir " + floor + ".");
        } else if (floor == null) {
            return new Exception("Nombre no encontrado. Quizá quiciste decir " + ceiling + ".");
        } else {
            return new Exception("Nombre no encontrado. Quizá quiciste decir " + floor + " ó " + ceiling + ".");
        }
    }

    /**
     * Ejecuta los procesos necesarios para lograr el requerimiento de persistencia.
     */
    public void cerrarProcesamiento() throws IOException {
        manejadorArchivos.guardarArchivos();
    }

    public ProcesamientoSupermercado(){
        try {
            manejadorArchivos = new ManejadorArchivos("data/clientes.txt","data/categorias.txt",
                    "data/productos.txt", "data/lotes.txt", "data/recibos.txt");
            productosPorCodigo = manejadorArchivos.getProductos();
            clientesPorCedula = manejadorArchivos.getClientes();
            categorias = manejadorArchivos.getCategorias();
            recibosSinCedula = manejadorArchivos.getRecibosSinCedula();
            productosPorNombre = new TreeMap<>();
            for(Producto producto: productosPorCodigo.values()){
                productosPorNombre.put(producto.getNombre(), producto);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
