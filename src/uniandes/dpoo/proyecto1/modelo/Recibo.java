package uniandes.dpoo.proyecto1.modelo;
import java.util.Date;
import java.util.ArrayList;

public class Recibo {
	private Date fecha;
	private ArrayList <CantidadProducto> cantidadesProductos;
	private ArrayList <Cliente> cliente;
	
	public Recibo (Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}
}
