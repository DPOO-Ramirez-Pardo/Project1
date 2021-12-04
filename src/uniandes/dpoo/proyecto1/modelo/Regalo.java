package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Regalo extends Promocion{
    private Producto producto;
    private int pague;
    private int lleve;

    public Regalo(String id, Date inicio, Date vencimiento, Producto producto, int pague, int lleve){
        super(id, inicio,vencimiento);
        this.producto = producto;
        this.pague = pague;
        this.lleve = lleve;
    }


    @Override
    public int aplicarPuntos(Recibo recibo) {
        return 0;
    }

    @Override
    public float aplicarDescuento(Recibo recibo) {
        for(CantidadProducto cantidadProducto : recibo.getCantidadesProductos()){
            if (cantidadProducto.getProducto().equals(producto)){
                try {
                    int n = (int)(cantidadProducto.getCantidad()) / (pague + lleve);
                    n *= lleve;
                    int m = (int)(cantidadProducto.getCantidad()) % (pague + lleve);
                    if(m > pague) n += m - pague;
                    return (float)(producto.precioProductos(n));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Override
    public String lineaRecibo(Recibo recibo) {
        return String.format("{} pague {} lleve {}: -{}", producto.getNombre(),
                pague, lleve, aplicarDescuento(recibo));
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
