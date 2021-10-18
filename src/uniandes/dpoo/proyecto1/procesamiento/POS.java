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

    public String generarRecibo() throws Exception {
        checkPedido();
        return actualRecibo.generarRecibo();
    }

    public String cerrarRecibo() throws Exception {
        checkPedido();
        String mensaje = actualRecibo.generarRecibo();
        actualRecibo.cerrar();
        actualRecibo = null;
        return mensaje;
    }

    public boolean existeCliente(int cedula){
        return (clientesPorCedula.get(cedula) != null);
    }

    public void registrarClienteEnSistemaPuntos(String nombre, int cedula, int edad, float puntos,
                                                Sexo sexo, SituacionEmpleo situacionEmpleo, EstadoCivil estadoCivil) throws Exception {
        if (existeCliente(cedula)) throw new Exception("El cliente ya existe.");
        else {
            clientesPorCedula.put(cedula,new Cliente(nombre, cedula, edad, puntos, sexo, situacionEmpleo, estadoCivil));
        }
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

    public void eliminarProductoPorNombre(String nombreProducto) throws Exception {
        checkPedido();
        Producto producto = getProductoPorNombre(nombreProducto);
        actualRecibo.eliminarProducto(producto);
    }

    public void eliminarProductoPorCodigo(int codigo) throws Exception {
        checkPedido();
        Producto producto = getProductoPorCodigo(codigo);
        actualRecibo.eliminarProducto(producto);
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

    public void cancelarProducto() {
        actualProducto = null;
    }

    public void añadirTitular(int cedula) {
        actualRecibo.añadirTitular(clientesPorCedula.get(cedula));
    }
}
