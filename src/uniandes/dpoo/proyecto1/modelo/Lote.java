package uniandes.dpoo.proyecto1.modelo;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Lote {

private final Date fechaLlegada;
private final Date fechaVencimiento;
private float cantidadInicial;
private float cantidadActual;

public Lote(Date fechaLlegada, Date fechaVencimiento, float cantidadInicial, float cantidadActual) {
	this.fechaLlegada = fechaLlegada;
	this.fechaVencimiento = fechaVencimiento;
	this.cantidadInicial = cantidadInicial;
	this.cantidadActual = cantidadActual;
}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public float getCantidadInicial() {
		return cantidadInicial;
	}

	public float getCantidadActual() {
		return cantidadActual;
	}

	public String lineaArchivo(){
		return DateFormat.getDateInstance().format(fechaLlegada) +","
				+DateFormat.getDateInstance().format(fechaVencimiento)+","
				+Float.toString(cantidadInicial)+","+Float.toString(cantidadActual);
	}

}
