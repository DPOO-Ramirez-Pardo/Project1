package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public abstract class Promocion {
    protected Date fechaInicio;
    protected Date fechaVencimiento;
    private String id;

    public Promocion(String id, Date fechaInicio, Date fechaVencimiento){
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
    }

    public abstract int aplicarPuntos(Recibo recibo);
    public abstract float aplicarDescuento(Recibo recibo);
    public abstract String lineaRecibo(Recibo recibo);
    protected abstract boolean cumpleRequisitosAdicionales(Recibo recibo);
    public abstract Collection<Producto> getProductos();

    public String getId() {
        return id;
    }

    public boolean seAplica(Recibo recibo, Date fecha){
        if (fecha.after(fechaInicio) && fecha.before(fechaVencimiento)){
            return cumpleRequisitosAdicionales(recibo);
        }
        else return false;
    }
}
