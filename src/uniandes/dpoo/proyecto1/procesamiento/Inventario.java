package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Categoria;
import uniandes.dpoo.proyecto1.modelo.Cliente;
import uniandes.dpoo.proyecto1.modelo.Producto;
import uniandes.dpoo.proyecto1.modelo.Recibo;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Inventario extends ProcesamientoSupermercado{

    public void eliminarLotesVencidosPorNombre(String nombreProducto) throws Exception {
        Producto producto = getProductoPorNombre(nombreProducto);
        producto.eliminarLotesVencidos(Calendar.getInstance().getTime());
    }

    public void eliminarLotesVencidosPorCodigo(int codigo) throws Exception {
        Producto producto = getProductoPorCodigo(codigo);
        if (producto == null) throw new Exception("No hay producto asociado al código.");
        else producto.eliminarLotesVencidos(Calendar.getInstance().getTime());
    }

    public void recibirCarga(String path) throws FileNotFoundException, ParseException {
        manejadorArchivos.cargarLotesPorPrimeraVez(path);
    }

    public void agregarCategoria(String nombre) throws Exception {
        if (categorias.containsKey(nombre)) throw new Exception("La categoría ya existe.");
        else {
            categorias.put(nombre, new Categoria(nombre));
        }
    }

    public void categorizarProductoPorNombre(String nombreCategoria, String nombreProducto) throws Exception {
        Producto producto = getProductoPorNombre(nombreProducto);
        Categoria categoria = getCategoriaPorNombre(nombreCategoria);
        categoria.añadirProducto(producto);
        producto.añadirCategoria(categoria);
    }

    public void categorizarProductoPorCodigo(String nombreCategoria, int codigoProducto) throws Exception {
        Producto producto = getProductoPorCodigo(codigoProducto);
        Categoria categoria = getCategoriaPorNombre(nombreCategoria);
        if (producto == null) throw new Exception("No hay producto asociado al código.");
        else {
            categoria.añadirProducto(producto);
            producto.añadirCategoria(categoria);
        }
    }

    public void agregarProducto(){

    }

    public void mostrarInformacionProducto(){

    }

    public Inventario(){
        super();
    }

    private void guardarRecibos(){

    }

    public String informacionInventarioProductoPorNombre(String nombre) throws Exception {
        Producto producto = getProductoPorNombre(nombre);
        return producto.stringInformacion();
    }

    public String informacionInventarioProductoPorCodigo(int codigo) throws Exception {
        Producto producto = getProductoPorCodigo(codigo);
        if (producto == null) throw new Exception("No hay producto asociado al código.");
        else return producto.stringInformacion();
    }

    public String desempenoFinancieroProductoPorNombre(String nombre) throws Exception {
        Producto producto = getProductoPorNombre(nombre);
        return producto.stringDesempeñoFinanciero();
    }

    public String desempenoFinancieroProductoPorCodigo(int codigo) throws Exception {
        Producto producto = getProductoPorCodigo(codigo);
        if (producto == null) throw new Exception("No hay producto asociado al código.");
        else return producto.stringDesempeñoFinanciero();
    }

    public String condicionAlmacenamientoPorCodigo(int codigo) throws Exception {
        Producto producto = getProductoPorCodigo(codigo);
        if (producto == null) throw new Exception("No hay producto asociado al código.");
        else return producto.getCondicion().toString();
    }

    public String condicionAlmacenamientoPorNombre(String nombre) throws Exception {
        Producto producto = getProductoPorNombre(nombre);
        return producto.getCondicion().toString();
    }

    public String mostrarInformacionProductoPorNombre(String nombreProducto) throws Exception {
        Producto producto = getProductoPorNombre(nombreProducto);
        return producto.stringInformacionLotes();
    }

    public String mostrarInformacionProductoPorCodigo(int codigo) throws Exception {
        Producto producto = getProductoPorCodigo(codigo);
        if (producto == null) throw new Exception("No hay producto asociado al código.");
        else return producto.stringInformacionLotes();
    }
}
