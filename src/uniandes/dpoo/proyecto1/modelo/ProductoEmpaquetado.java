package uniandes.dpoo.proyecto1.modelo;
import java.text.MessageFormat;
import java.util.ArrayList;

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
		eliminarLotesVencidos();
		float precio = 0;
		for (Lote lote: lotes){
			if (cantidad <= lote.getCantidadActual()){
				precio += cantidad * peso * lote.getPrecioUnidadAdquisicion();
				cantidad = 0;
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
	public String lineaArchivo(){
		return super.lineaArchivo() + "," + Float.toString(peso);
	}

	@Override
	public String stringInformacion(){
		try{
			return MessageFormat.format(
					"""
                            Producto: {}
                            Descripción: {}
                            Código de Barras: {}
                            Condición de Almacenamiento: {}
                            Costo de Adquisición del Paquete: {}
                            Precio de Venta del Paquete: {}
                            Precio por {}: {}
                            {} {} por paquete.
                            Cantidad Paquetes: {}
                            """, nombre, descripcion, codigo, condicion,
					costoProductos(1), precioProductos(1), unidad, getPrecioPorUnidad(), peso, unidad
			);
		} catch (Exception e){
			return null;
		}
	}

	@Override
	public String stringDesempeñoFinanciero() {
		return MessageFormat.format("""
				Cantidad de Paquetes Vendidos: {}
				Cantidad de Paquetes Deshechados: {}
				Dinero Adquirido por el Producto: {}
				""", cantidadVendida, cantidadDeshechada, dineroAdquirido);
	}
}
