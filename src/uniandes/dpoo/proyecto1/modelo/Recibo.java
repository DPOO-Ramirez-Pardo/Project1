package uniandes.dpoo.proyecto1.modelo;
import java.text.DateFormat;
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

	public Cliente getCliente() {
		return cliente;
	}

	public ArrayList<CantidadProducto> getCantidadesProductos() {
		return cantidadesProductos;
	}

	public Date getFecha() {
		return fecha;
	}

	public String lineaArchivo(){
		StringBuilder builder = new StringBuilder().append(cliente.getCedula()).append(",")
				.append(DateFormat.getDateInstance().format(fecha));
		for (int i = 0; i < cantidadesProductos.size(); i++) {
			builder.append(",").append(cantidadesProductos.get(i).getCantidad()).append(",")
					.append(cantidadesProductos.get(i).getProducto().getCodigo());
		}
		return builder.toString();
	}
}
