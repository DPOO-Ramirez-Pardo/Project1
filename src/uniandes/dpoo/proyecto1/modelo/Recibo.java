package uniandes.dpoo.proyecto1.modelo;
import uniandes.dpoo.proyecto1.exceptions.ClienteNoAñadidoException;
import uniandes.dpoo.proyecto1.exceptions.PuntosMayoresTotalException;
import uniandes.dpoo.proyecto1.exceptions.SinPuntosSuficientesException;

import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Recibo {
	private Date fecha;
	private Cliente cliente;
	private ArrayList <CantidadProducto> cantidadesProductos;
	private HashMap<Promocion, Number[]> promociones;
	private float subtotal;
	private int puntosAntes;
	private int puntosRedimidos;



	public Recibo (Date fecha, Cliente cliente, ArrayList<CantidadProducto> cantidadesProductos, ArrayList<Promocion> promociones, float subtotal,
				   int puntosAntes, int puntosRedimidos){
		this.fecha = fecha;
		this.cliente = cliente;
		this.cantidadesProductos = cantidadesProductos;
		this.subtotal = subtotal;
		this.puntosAntes = puntosAntes;
		this.puntosRedimidos = puntosRedimidos;
		this.promociones = new HashMap<>();
		for (Promocion promocion: promociones){
			this.promociones.put(promocion,
					new Number[] {promocion.aplicarDescuento(this), promocion.aplicarPuntos(this)});
		}
	}

	public Recibo (Date fecha) {
		this(fecha, null, new ArrayList<>(), new ArrayList<>(), 0, 0, 0);
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

	/**
	 *
	 * @return El texto de la línea que corresponde a la información del recibo en recibos.txt.
	 */
	public String lineaArchivo(){
		StringBuilder builder = new StringBuilder().append(cliente != null ? cliente.getCedula(): 0).append(",")
				.append(DateFormat.getDateInstance().format(fecha)).append(",").append(puntosAntes).append(",")
				.append(puntosRedimidos);
		for (int i = 0; i < cantidadesProductos.size(); i++) {
			try {builder.append(",").append(cantidadesProductos.get(i).getCantidad()).append(",")
					.append(cantidadesProductos.get(i).getProducto().getCodigo()).append(",")
					.append(cantidadesProductos.get(i).getCosto());} catch (Exception e) {}
		}
		for (Promocion promocion: promociones.keySet()) {
			builder.append(',').append(promocion.getId());
		}
		return builder.append(",").append(subtotal).toString();
	}

	/**
	 * Elimina el producto del recibo.
	 * @param producto Producto que se eliminará.
	 * @throws Exception Si el producto no está en el Recibo.
	 */
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

	/**
	 * Agrega la cantidad especificada del producto al recibo.
	 * @param producto Producto que va a agregarse.
	 * @param cantidad Cantidad a agregarse.
	 * @throws Exception Si no existe está disponible esa cantidad del producto.
	 */
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
			subtotal += cantidadProducto.getCosto();
			cantidadesProductos.add(cantidadProducto);
		}
	}

	public float getTotal(){
		return (float) (subtotal*1.19);
	}

	/**
	 * @return String correspondiente al Recibo
	 * @throws Exception En ningún caso (habría botado excepción en agregarCantidadProducto).
	 */
	public String generarRecibo() throws Exception {
		StringBuilder builder = new StringBuilder();
		generarSeccionCabeza(builder);
		generarSeccionProductos(builder);
		generarSeccionPromociones(builder);
		generarSeccionExtracto(builder);
		if (cliente != null) generarSeccionPuntos(builder);
		return builder.toString();
	}

	private void generarSeccionPuntos(StringBuilder builder) {
		builder.append("\n-----\n").append("Puntos").append("\n-----\n")
				.append("Puntos Acumulados Antes de la Compra: ").append(puntosAntes)
				.append("\n").append("Puntos Redimidos en la Compra: ").append(puntosRedimidos)
				.append("\n").append("Puntos Acumulados en la Compra: ").append(getPuntosAcumulados())
				.append("\n").append("Puntos Acumulados Después de la Compra: ").append(getPuntosDespues());
	}

	private void generarSeccionExtracto(StringBuilder builder) {
		builder.append("\n-----\n").append("Extracto").append("\n-----\n");
		float subtotal = getSubtotalDespuesDePromociones();
		builder.append("subtotal: ").append(subtotal).append("\n")
				.append("IVA: ").append(subtotal*0.19).append("\n")
				.append("total: ").append(subtotal*1.19).append("\n");
	}

	private void generarSeccionCabeza(StringBuilder builder) {
		builder.append("\n-----\n").append("Cabeza").append("\n-----\n");
		builder.append("fecha: ").append(fecha).append("\n");
		if (cliente != null) builder.append("cliente: ").append(cliente.getNombre());
	}

	private void generarSeccionProductos(StringBuilder builder) throws Exception {
		builder.append("\n-----\n").append("Productos").append("\n-----\n");
		for (CantidadProducto cantidadProducto: cantidadesProductos) {
			builder.append(cantidadProducto.getCantidad()).append(" ")
					.append(cantidadProducto.getProducto().getNombre()).append(" ")
					.append(cantidadProducto.getCosto()).append("\n");
		}
	}

	private void generarSeccionPromociones(StringBuilder builder) {
		builder.append("\n-----\n").append("Promociones").append("\n-----\n");
		for (Promocion promocion: promociones.keySet()){
			builder.append(promocion.lineaRecibo(this)).append("\n");
		}
	}

	/**
	 * Registra los cambios en el sistema por generar el Recibo.
	 */
	public void cerrar() {
		if (cliente != null) cliente.añadirRecibo(this);
		for(CantidadProducto cantidadProducto: cantidadesProductos){
			try {cantidadProducto.reducirCantidadEnLotes();} catch (Exception e) {}
			// Como reducirCantidadEnLotes bota una excepción hay que hacer un try/catch statement.
			// Sin embargo, en la práctica esa excepción nunca ocurrirá.
		}
		if (cliente != null) cliente.añadirPuntos((int)(subtotal * 1.19 / 1000));
	}

    public void añadirTitular(Cliente cliente) {
		this.cliente = cliente;
		if (cliente != null){
			this.puntosAntes = cliente.getPuntos();
		}
		else this.puntosAntes = 0;
		this.puntosRedimidos = 0;
    }

	public void redimirPuntos(int puntosRedimidos) throws ClienteNoAñadidoException,
			PuntosMayoresTotalException, SinPuntosSuficientesException {
		float subtotal = getSubtotalDespuesDePromociones();
		if (cliente == null) throw new ClienteNoAñadidoException();
		else if (subtotal * 1.19 - puntosRedimidos * 15 <= -15) throw new PuntosMayoresTotalException(this);
		else {
			if (cliente.getPuntos() - puntosRedimidos < 0) throw new SinPuntosSuficientesException(cliente);
			else {
				this.puntosRedimidos = puntosRedimidos;
			}
		}
	}

	public int getPuntosAntes() {
		return puntosAntes;
	}

	public int getPuntosAcumulados() {
		float subtotal = getSubtotalDespuesDePromociones();
		int puntos = (int)((subtotal*1.19 - puntosRedimidos * 15) / 1000);
		for (Number numbers[] : promociones.values()){
			puntos += (Integer) numbers[1];
		}
		return puntos;
	}

	public int getPuntosRedimidos() {
		return puntosRedimidos;
	}

	public int maximoPuntosRedimidos() {
		float subtotal = getSubtotalDespuesDePromociones();
		if (cliente == null) return 0;
		int puntosCompra = (int) (subtotal * 1.19 / 15);
		if(subtotal * 1.19 - puntosCompra * 15 > 0) puntosCompra++;
		return Math.min(puntosCompra, cliente.getPuntos());
	}

	public int getPuntosDespues(){
		return puntosAntes + getPuntosAcumulados() - puntosRedimidos;
	}

	public void añadirPromocion(Promocion promocion) {
		if (!promociones.containsKey(promocion)) promociones.put(promocion, new Number[]{0,0});
		promociones.get(promocion)[0] = promocion.aplicarDescuento(this);
		promociones.get(promocion)[1] = promocion.aplicarPuntos(this);
	}

	public void eliminarPromocion(Promocion promocion) {
		promociones.remove(subtotal);
	}

	private float getSubtotalDespuesDePromociones(){
		float customSubtotal = subtotal;
		for (Number numbers[] : promociones.values()){
			customSubtotal -= (Float) numbers[0];
		}
		return customSubtotal;
	}
}
