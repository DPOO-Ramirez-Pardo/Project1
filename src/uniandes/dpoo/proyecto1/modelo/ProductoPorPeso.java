package uniandes.dpoo.proyecto1.modelo;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ProductoPorPeso extends Producto{
	public ProductoPorPeso (String nombre, String descripcion, String pathImagen, int codigo, CondicionAlmacenamiento condicion,
							float cantidadVendida, float cantidadDeshechada, float dineroAdquirido, String unidad) {
		super(nombre, descripcion, pathImagen, codigo, condicion, cantidadVendida, cantidadDeshechada, dineroAdquirido, unidad);
	}

	@Override
	public float costoProductos(float cantidad) throws Exception {
		float precio = 0;
		for (Lote lote: lotes){
			if(lote.getFechaVencimiento().before(Calendar.getInstance().getTime())){
				eliminarLoteVencido(lote);
			}
			else if (cantidad <= lote.getCantidadActual()){
				precio += cantidad * lote.getPrecioUnidadAdquisicion();
				cantidad = 0;
			} else {
				precio += lote.getCantidadActual() * lote.getPrecioUnidadAdquisicion();
				cantidad -= lote.getCantidadActual();
			}
		}
		if (cantidad > 0) throw new Exception("La cantidad excede la disponible por "+Float.toString(cantidad));
		else return precio;
	}

	@Override
	public float precioProductos(float cantidad){
		return cantidad * getPrecioPorUnidad();
	}

	@Override
	protected void eliminarLoteVencido(Lote lote) {
		cantidadDeshechada += lote.getCantidadActual();
		dineroAdquirido -= lote.getCantidadActual() * lote.getPrecioUnidadAdquisicion();
		lotes.remove(lote);
	}

	@Override
	public String stringInformacion() {
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
                            Costo de Adquisición por {4}: {5}
                            Precio por {4}: {6}
                            Cantidad Total {4}: {7}
                            Categorias: {8}
                            """, nombre, descripcion, Integer.toString(codigo), condicion.toString(),
							unidad, Float.toString(getCostoPorUnidadAdquisicion()), Float.toString(getPrecioPorUnidad()),
							cantidadActual, categoriasBuilder.toString()
			);
		} catch (Exception e){
			return null;
		}
	}

	@Override
	public String stringDesempeñoFinanciero() {
		return MessageFormat.format("""
				Cantidad de {0} Vendidos: {1}
				Cantidad de {0} Deshechados: {2}
				Dinero Adquirido por el Producto: {3}
				""", unidad, cantidadVendida, cantidadDeshechada, dineroAdquirido);
	}
}
