package uniandes.dpoo.proyecto1.modelo;

public class CantidadProducto {
	private float cantidad;
	private Producto producto;
	public CantidadProducto (float cantidad, Producto producto) throws Exception {
		this.producto = producto;
		if(producto instanceof ProductoEmpaquetado && cantidad != Math.ceil(cantidad))
			throw new Exception("La cantidad debe ser entera");
		producto.costoProductos(cantidad);
		this.cantidad = cantidad;
	}

	public float getCantidad() {
		return cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public float getCosto() throws Exception {
		return producto.costoProductos(cantidad);
	}

    public void aumentarCantidad(float cantidad) throws Exception {
		if(producto instanceof ProductoEmpaquetado && cantidad != Math.ceil(cantidad))
			throw new Exception("La cantidad debe ser entera");
		producto.costoProductos(this.cantidad+cantidad);
		this.cantidad += cantidad;
    }

	public void reducirCantidadEnLotes() throws Exception {
		producto.reducirCantidad(cantidad);
	}
}
