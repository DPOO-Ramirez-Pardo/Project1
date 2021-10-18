package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Categoria;
import uniandes.dpoo.proyecto1.modelo.Cliente;
import uniandes.dpoo.proyecto1.modelo.Producto;
import uniandes.dpoo.proyecto1.modelo.Recibo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Inventario extends ProcesamientoSupermercado{

    private void eliminarLotesVencidosPorNombre(String nombreProducto) throws Exception {
        Producto producto = getProductoPorNombre(nombreProducto);

    }

    private void eliminarLotesVencidosPorCodigo(int codigo) throws Exception {
        Producto producto = getProductoPorCodigo(codigo);
        if (producto == null) throw new Exception("No hay producto asociado al código.");
        else producto.eliminarLotesVencidos(Calendar.getInstance().getTime());
    }

    private void informacionInventarioProducto(){

    }

    public void desempenoFinancieroProducto(){

    }

    public void recibirCarga(){

    }

    public String condicionAlmacenamiento(String nombreProducto){
        return null;
    }

    public void agregarCategoria(){

    }

    public void categorizarProducto(){

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

}
