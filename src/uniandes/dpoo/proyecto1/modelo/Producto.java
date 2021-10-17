package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public abstract class Producto {
	protected String nombre;
	protected String descripcion;
	protected int codigo;
	protected CondicionAlmacenamiento condicion;
	protected float cantidadVendida;
	protected float dineroAdquirido;
	protected LinkedList<Lote> lotes;
	protected ArrayList<Categoria> categorias;
	protected String unidad;

	public Producto(String nombre, String descripcion, int codigo, CondicionAlmacenamiento condicion,
					float cantidadVendida, float dineroAdquirido, String unidad) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.condicion = condicion;
		this.cantidadVendida = cantidadVendida;
		this.dineroAdquirido = dineroAdquirido;
		this.lotes = new LinkedList<>();
		this.categorias = new ArrayList<>();
		this.unidad = unidad;
	}


	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getCodigo() {
		return codigo;
	}

	public CondicionAlmacenamiento getCondicion() {
		return condicion;
	}

	public float getCantidadVendida() {
		return cantidadVendida;
	}

	public float getDineroAdquirido() {
		return dineroAdquirido;
	}

	public LinkedList<Lote> getLotes() {
		return lotes;
	}

	public ArrayList<Categoria> getCategorias() {
		return categorias;
	}

	public String getUnidad() {
		return unidad;
	}


	public abstract float costoProductos(float cantidad);

	public String mostrarCategorias() {
		StringBuilder builder = new StringBuilder(categorias.get(0).getNombre());
		for (int i = 1; i < categorias.size(); i++) {
			builder.append(", " + categorias.get(i).getNombre());
		}
		return builder.toString();
	}

	public String mostrarCondicionAlmacenamiento(){
		return condicion.toString();
	}

	public void añadirLote(Lote lote) {
		lotes.add(lote);
	}

	public void añadirCategoria(Categoria categoria){
		categorias.add(categoria);
	}

	public void eliminarLotesVencidos(Date fecha){
		while (lotes.getFirst().getFechaVencimiento().before(fecha)){
			lotes.removeFirst();
		}
	}

	public String lineaArchivo(){
		return nombre +","+descripcion+","+Integer.toString(codigo)+","+condicion.toString()+","
				+Float.toString(cantidadVendida)+","+Float.toString(dineroAdquirido)+","+unidad;
	}

	public abstract String stringInformacion();
}

