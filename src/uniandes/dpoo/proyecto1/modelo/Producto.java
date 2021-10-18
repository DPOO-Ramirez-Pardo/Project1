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
	protected float cantidadDeshechada;
	protected float dineroAdquirido;
	protected ArrayList<Lote> lotes;
	protected ArrayList<Categoria> categorias;
	protected String unidad;
	protected float cantidadActual;

	public Producto(String nombre, String descripcion, int codigo, CondicionAlmacenamiento condicion,
					float cantidadVendida, float cantidadDeshechada, float dineroAdquirido, String unidad) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.condicion = condicion;
		this.cantidadVendida = cantidadVendida;
		this.cantidadDeshechada = cantidadDeshechada;
		this.dineroAdquirido = dineroAdquirido;
		this.lotes = new ArrayList<>();
		this.categorias = new ArrayList<>();
		this.unidad = unidad;
		this.cantidadActual = 0;
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

	public ArrayList<Lote> getLotes() {
		return lotes;
	}

	public ArrayList<Categoria> getCategorias() {
		return categorias;
	}

	public String getUnidad() {
		return unidad;
	}

	public abstract float costoProductos(float cantidad) throws Exception;

	public abstract float precioProductos(float cantidad) throws Exception;

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
		cantidadActual += lote.getCantidadActual();
	}

	public void añadirCategoria(Categoria categoria){
		categorias.add(categoria);
	}

	public void eliminarLotesVencidos(Date fecha){
		for (int i = 0; i < lotes.size(); ++i){
			Lote lote = lotes.get(i);
			if(lote.getFechaVencimiento().before(fecha)){
				eliminarLoteVencido(lote);
				--i;
			}
		}
	}

	protected abstract void eliminarLoteVencido(Lote lote);

	public String lineaArchivo(){
		return nombre +","+descripcion+","+Integer.toString(codigo)+","+condicion.toString()+","
				+Float.toString(cantidadVendida)+","+Float.toString(cantidadDeshechada)+","
				+Float.toString(dineroAdquirido)+","+unidad;
	}

	public float getCostoPorUnidadAdquisicion(){
		return lotes.get(0).getPrecioUnidadAdquisicion();
	}

	public float getPrecioPorUnidad(){
		return lotes.get(lotes.size()-1).getPrecioVentaAlPublico();
	}

	public abstract String stringInformacion();

	public abstract String stringDesempeñoFinanciero();

	public void reducirCantidad(float cantidad) throws Exception {
		dineroAdquirido += precioProductos(cantidad) - costoProductos(cantidad);
		cantidadVendida += cantidad;
		for(Lote lote: lotes){
			if (lote.getCantidadActual() <= cantidad){
				cantidad -= lote.getCantidadActual();
				lotes.remove(lote);
				if (cantidad == 0) return;
			} else {
				lote.reducirCantidadActual(cantidad);
				return;
			}
		}
	}

    public String stringInformacionLotes() {
		StringBuilder builder = new StringBuilder();
		for(Lote lote: lotes){
			builder.append(lote.stringInformacion(unidad)).append('\n');
		}
		return builder.toString();
    }
}

