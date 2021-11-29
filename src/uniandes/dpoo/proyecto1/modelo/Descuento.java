package uniandes.dpoo.proyecto1.modelo;

import java.util.Date;

public class Descuento extends Promocion{
    private Producto producto;
    private float porcentaje;

    public Descuento(Date inicio, Date vencimiento, Producto producto, float porcentaje){
        super(inicio,vencimiento);
        this.producto = producto;
        this.porcentaje = porcentaje;
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
                    return (float)(cantidadProducto.getCosto() * 1.19 * porcentaje);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Override
    public String lineaRecibo(Recibo recibo) {
        return "Descuento "+ producto.getNombre() + " "+ porcentaje +"%: -"+aplicarDescuento(recibo);
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
}
