package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.*;

import java.util.ArrayList;
import java.util.Calendar;

public class POS extends ProcesamientoSupermercado{

    private Recibo actualRecibo;
    private Producto actualProducto;

    public POS(){
        actualRecibo = null;
    }

    public boolean tienePedido(){
        return (actualRecibo != null);
    }

    private void generarRecibo(){

    }

    private void registrarClienteEnSistemaPuntos(){

    }

    public void agregarCantidadProducto(float cantidad) throws Exception {
        checkPedido();
        checkProducto();
        actualRecibo.agregarCantidadProducto(actualProducto, cantidad);
        actualProducto = null;
    }

    private void checkPedido() throws Exception {
        if (actualRecibo == null) throw new Exception("No hay pedido en curso.");
    }

    public String unidadActualProducto() throws Exception {
        checkPedido();
        checkProducto();
        return actualProducto.getUnidad();
    }

    private void checkProducto() throws Exception {
        if (actualProducto == null) throw new Exception("No hay producto para agregar cantidad.");
    }

    public void agregarProductoPorNombre(String nombreProducto) throws Exception {
        checkPedido();
        if (actualProducto == null){
            Producto producto = getProductoPorNombre(nombreProducto);
            actualProducto = producto;
        } else throw new Exception("Ya hay un producto (" + actualProducto.getNombre() + ") siendo agregado.");
    }

    public String agregarProductoPorCodigo(int codigo) throws Exception {
        checkPedido();
        if (actualProducto == null){
            Producto producto = getProductoPorCodigo(codigo);
            actualProducto = producto;
            return producto.getNombre();
        } else throw new Exception("Ya hay un producto siendo agregado.");
    }

    private void eliminarProducto(String nombreProducto) throws Exception {
        checkPedido();
    }

    public void iniciarPedido() throws Exception {
        if (actualRecibo == null){
            actualRecibo = new Recibo(Calendar.getInstance().getTime());
        } else throw new Exception("Hay un pedido en curso.");
    }

    public boolean actualProductoEsEmpaquetado() throws Exception {
        checkPedido();
        checkProducto();
        return actualProducto instanceof ProductoEmpaquetado;
    }

    public void eliminarProducto() {

    }
}
