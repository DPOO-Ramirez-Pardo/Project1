package uniandes.dpoo.proyecto1.modelo;

public class CantidadProducto {
	private float cantidad;

	private Producto producto;
	public CantidadProducto (float cantidad, Producto producto) throws Exception {
		this.producto = producto;
		if(producto instanceof ProductoEmpaquetado){
			if (cantidad != Math.ceil(cantidad)) throw new Exception("La cantidad debe ser entera");
		}
		this.cantidad = cantidad;
	}

	public float getCantidad() {
		return cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

    public void aumentarCantidad(float cantidad) {

    }
}
