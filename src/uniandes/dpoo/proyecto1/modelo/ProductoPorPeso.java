package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;

public class ProductoPorPeso extends Producto{
	public ProductoPorPeso (String nombre, String descripcion, int codigo, CondicionAlmacenamiento condicion,
							float cantidadVendida, float cantidadDeshechada, float dineroAdquirido, String unidad) {
		super(nombre, descripcion, codigo, condicion, cantidadVendida, cantidadDeshechada, dineroAdquirido, unidad);
	}

	@Override
	public float costoProductos(float cantidad) throws Exception {
		return 0;
	}

	@Override
	public float precioProductos(float cantidad) {
		return 0;
	}

	@Override
	public String stringInformacion() {
		return null;
	}

	@Override
	public String stringDesempeñoFinanciero() {
		return null;
	}
}
