package uniandes.dpoo.proyecto1.modelo;

public class CantidadProducto {
	private float cantidad;

	private Producto producto;
	public CantidadProducto (float cantidad, Producto producto) {
		this.cantidad = cantidad;
		this.producto = producto;
	}

	public float getCantidad() {
		return cantidad;
	}

	public Producto getProducto() {
		return producto;
	}
	
}
