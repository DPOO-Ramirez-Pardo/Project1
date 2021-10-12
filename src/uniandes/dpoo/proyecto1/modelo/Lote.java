package uniandes.dpoo.proyecto1.modelo;
import java.util.Date;
import java.util.ArrayList;

public class Lote {

private final Date fechaLlegada;
private final Date fechaVencimiento;
private final float cantidad;

public Lote(Date fechaLlegada, Date fechaVencimiento, float cantidad) {
	this.fechaLlegada = fechaLlegada;
	this.fechaVencimiento = fechaVencimiento;
	this.cantidad = cantidad;
}

public Date getFechaLlegada() {
	return fechaLlegada;
}

public Date getFechaVencimiento() {
	return fechaVencimiento;
}

public float getCantidad() {
	return cantidad;
}
}
