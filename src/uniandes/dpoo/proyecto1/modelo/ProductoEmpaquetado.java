package uniandes.dpoo.proyecto1.modelo;
import java.text.MessageFormat;
import java.util.ArrayList;

public class ProductoEmpaquetado extends Producto{
	private float peso;
	public ProductoEmpaquetado (String nombre, String descripcion, int codigo, CondicionAlmacenamiento condicion,
								float cantidadVendida, float dineroAdquirido, String unidad, float peso) {
		super(nombre, descripcion, codigo, condicion, cantidadVendida, dineroAdquirido, unidad);
		this.peso = peso;
	}
	public float getPeso() {
		return peso;
	}

	@Override
	public float costoProductos(float cantidad) {
		return 0;
	}

	@Override
	public String lineaArchivo(){
		return super.lineaArchivo() + "," + Float.toString(peso);
	}

	@Override
	public String stringInformacion() {
		return MessageFormat.format(
				"""
						Producto: {0}
						Descripción: {1}
						Código de Barras: {2}
						Condición de Almacenamiento: {3}
						
						""", nombre, descripcion, codigo, condicion,
		);
	}
}
