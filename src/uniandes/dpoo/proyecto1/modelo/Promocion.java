package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;
import java.util.Date;

public abstract class Promocion {
    protected Date fechaInicio;
    protected Date fechaVencimiento;

    public Promocion(Date fechaInicio, Date fechaVencimiento){
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
    }

    public abstract int aplicarPuntos(Recibo recibo);
    public abstract float aplicarDescuento(Recibo recibo);
    public abstract String lineaRecibo(Recibo recibo);
    protected abstract boolean cumpleRequisitosAdicionales(Recibo recibo);

    public boolean seAplica(Recibo recibo, Date fecha){
        if (fecha.after(fechaInicio) && fecha.before(fechaVencimiento)){
            return cumpleRequisitosAdicionales(recibo);
        }
        else return false;
    }
}
