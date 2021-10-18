package uniandes.dpoo.proyecto1.modelo;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Recibo {
	private Date fecha;
	private Cliente cliente;
	private ArrayList <CantidadProducto> cantidadesProductos;
	private float subtotal;

	public Recibo (Date fecha, Cliente cliente, ArrayList<CantidadProducto> cantidadesProductos){
		this.fecha = fecha;
		this.cliente = cliente;
		this.cantidadesProductos = cantidadesProductos;
		this.subtotal = 0;
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

	public void eliminarProducto(Producto producto) throws Exception {
		boolean yaExiste = false;
		for(int i = 0; i < cantidadesProductos.size(); i++){
			if (producto == cantidadesProductos.get(i).getProducto()){
				yaExiste = true;
				subtotal -= cantidadesProductos.get(i).getCosto();
				cantidadesProductos.remove(i);
				break;
			}
		}
		if(!yaExiste) throw new Exception("El producto no hace parte del Recibo");
	}

	public void agregarCantidadProducto(Producto producto, float cantidad) throws Exception {
		boolean yaExiste = false;
		for(CantidadProducto cantidadProducto: cantidadesProductos){
			Producto otroProducto = cantidadProducto.getProducto();
			if (producto == otroProducto){
				yaExiste = true;
				subtotal -= cantidadProducto.getCosto();
				cantidadProducto.aumentarCantidad(cantidad);
				subtotal += cantidadProducto.getCosto();
				break;
			}
		}
		if(!yaExiste){
			CantidadProducto cantidadProducto = new CantidadProducto(cantidad, producto);
			cantidadesProductos.add(cantidadProducto);
		}
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String generarRecibo() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("fecha: ").append(fecha);
		if (cliente != null) builder.append("cliente: ").append(cliente.getNombre()).append("\n");
		for (CantidadProducto cantidadProducto: cantidadesProductos) {
			builder.append(cantidadProducto.getCantidad()).append(" ")
					.append(cantidadProducto.getProducto().getNombre()).append(" ")
					.append(cantidadProducto.getCosto()).append("\n");
		}
		builder.append("subtotal: ").append(subtotal).append("\n")
				.append("IVA: ").append(subtotal*0.19).append("\n")
				.append("total: ").append(subtotal*1.19).append("\n");
		return builder.toString();
	}

	public void cerrar() {
		cliente.añadirRecibo(this);
		for(CantidadProducto cantidadProducto: cantidadesProductos){
			try {cantidadProducto.reducirCantidadEnLotes();} catch (Exception e) {}
		}
		cliente.añadirPuntos((float) (subtotal * 1.19 / 1000));
	}
}
