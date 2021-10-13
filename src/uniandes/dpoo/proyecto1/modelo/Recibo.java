package uniandes.dpoo.proyecto1.modelo;
import java.util.Date;
import java.util.ArrayList;

public class Recibo {
	private Date fecha;
	private Cliente cliente;
	private ArrayList <CantidadProducto> cantidadesProductos;

	public Recibo (Date fecha, Cliente cliente, ArrayList<CantidadProducto> cantidadesProductos){
		this.fecha = fecha;
		this.cliente = cliente;
		this.cantidadesProductos = cantidadesProductos;
	}

	public Recibo (Date fecha) {
		this(fecha, null, new ArrayList<>());
	}

	public Date getFecha() {
		return fecha;
	}

	public String lineaArchivo(){
		StringBuilder builder = new StringBuilder(cliente.getCedula()).append(",").append(fecha);
		for (int i = 0; i < cantidadesProductos.size(); i++) {
			builder.append(",").append(cantidadesProductos.get(i).getCantidad()).append(",")
					.append(cantidadesProductos.get(i).getProducto().getCodigo());
		}
		return builder.toString();
	}
}
