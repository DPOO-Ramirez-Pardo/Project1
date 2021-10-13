package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;

public class ProductoPorPeso extends Producto{
	public ProductoPorPeso (String nombre, String descripcion, int codigo, CondicionAlmacenamiento condicion,
							float cantidadVendida, float dineroAdquirido, String unidad) {
		super(nombre, descripcion, codigo, condicion, cantidadVendida, dineroAdquirido, unidad);
	}

	@Override
	public float costoProductos(float cantidad) {
		return 0;
	}
}
