package uniandes.dpoo.proyecto1.modelo;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ProductoEmpaquetado extends Producto{
	private float peso;
	public ProductoEmpaquetado (String nombre, String descripcion, int codigo, CondicionAlmacenamiento condicion,
								float cantidadVendida, float cantidadDeshechada, float dineroAdquirido, String unidad, float peso) {
		super(nombre, descripcion, codigo, condicion, cantidadVendida, cantidadDeshechada, dineroAdquirido, unidad);
		this.peso = peso;
	}
	public float getPeso() {
		return peso;
	}

	@Override
	public float costoProductos(float cantidad) throws Exception {
		float precio = 0;
		for (int i = 0; i < lotes.size(); ++i){
			Lote lote = lotes.get(i);
			if(lote.getFechaVencimiento().before(Calendar.getInstance().getTime())){
				eliminarLoteVencido(lote);
				i--;
			}
			else if (cantidad <= lote.getCantidadActual()){
				precio += cantidad * peso * lote.getPrecioUnidadAdquisicion();
				cantidad = 0;
				break;
			} else {
				precio += lote.getCantidadActual() * peso * lote.getPrecioUnidadAdquisicion();
				cantidad -= lote.getCantidadActual();
			}
		}
		if (cantidad > 0) throw new Exception("La cantidad excede la disponible por "+Float.toString(cantidad));
		else return precio;
	}

	@Override
	public float precioProductos(float cantidad) {
		return cantidad * peso * getPrecioPorUnidad();
	}

	@Override
	protected void eliminarLoteVencido(Lote lote) {
		cantidadDeshechada += lote.getCantidadActual();
		cantidadActual -= lote.getCantidadActual();
		dineroAdquirido -= lote.getCantidadActual() * lote.getPrecioUnidadAdquisicion();
		lotes.remove(lote);
	}

	@Override
	public String lineaArchivo(){
		return super.lineaArchivo() + "," + Float.toString(peso);
	}

	@Override
	public String stringInformacion(){
		try{
			StringBuilder categoriasBuilder = new StringBuilder();
			for (Categoria categoria: categorias){
				categoriasBuilder.append(categoria.getNombre()).append(", ");
			}
			return MessageFormat.format(
					"""
                            Producto: {0}
                            Descripción: {1}
                            Código de Barras: {2}
                            Condición de Almacenamiento: {3}
                            Costo de Adquisición del Paquete: {4}
                            Precio de Venta del Paquete: {5}
                            Precio por {6}: {7}
                            {8} {6} por paquete.
                            Cantidad Paquetes: {9}
                            Categorias: {10}
                            """, nombre, descripcion, Integer.toString(codigo), condicion.toString(),
							Float.toString(costoProductos(1)), Float.toString(precioProductos(1)),
							unidad, Float.toString(getPrecioPorUnidad()), Float.toString(peso), cantidadActual,
							categoriasBuilder.toString()
			);
		} catch (Exception e){
			return null;
		}
	}

	@Override
	public String stringDesempeñoFinanciero() {
		return MessageFormat.format("""
				Cantidad de Paquetes Vendidos: {0}
				Cantidad de Paquetes Deshechados: {1}
				Dinero Adquirido por el Producto: {2}
				""", cantidadVendida, cantidadDeshechada, dineroAdquirido);
	}
}
