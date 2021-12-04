package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class PuntosMultiplicados extends Promocion{
    private Producto producto;
    private int multiplicador;

    public PuntosMultiplicados(String id, Date inicio, Date vencimiento, Producto producto, int multiplicador){
        super(id, inicio,vencimiento);
        this.producto = producto;
        this.multiplicador = multiplicador;
    }


    @Override
    public int aplicarPuntos(Recibo recibo) {
        for(CantidadProducto cantidadProducto : recibo.getCantidadesProductos()){
            if (cantidadProducto.getProducto().equals(producto)){
                try {
                    return (int)(cantidadProducto.getCosto() * 1.19 * (multiplicador-1) / 1000);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Override
    public float aplicarDescuento(Recibo recibo) {
        return 0;
    }

    @Override
    public String lineaRecibo(Recibo recibo) {
        return "Multiplicador puntos "+ producto.getNombre() + " x"+multiplicador +": "+aplicarPuntos(recibo);
    }

    @Override
    public boolean cumpleRequisitosAdicionales(Recibo recibo) {
        for(CantidadProducto cantidadProducto : recibo.getCantidadesProductos()){
            if (cantidadProducto.getProducto().equals(producto)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Producto> getProductos() {
        ArrayList<Producto> listaProductos = new ArrayList<>();
        listaProductos.add(producto);
        return listaProductos;
    }
}
