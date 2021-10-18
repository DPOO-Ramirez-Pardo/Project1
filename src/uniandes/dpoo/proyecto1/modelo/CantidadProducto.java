package uniandes.dpoo.proyecto1.modelo;

public class CantidadProducto {
	private float cantidad;
	private Producto producto;
	private float costo;

	public CantidadProducto (float cantidad, Producto producto, float costo) throws Exception {
		this.producto = producto;
		if(producto instanceof ProductoEmpaquetado && cantidad != Math.ceil(cantidad))
			throw new Exception("La cantidad debe ser entera");
		producto.costoProductos(cantidad);
		this.cantidad = cantidad;
		this.costo = costo;
	}

	public CantidadProducto(float cantidad, Producto producto) throws Exception{
		this(cantidad, producto, 0);
	}

	public float getCantidad() {
		return cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public float getCosto() throws Exception {
		if(costo != 0) return costo;
		else return producto.precioProductos(cantidad);
	}

    public void aumentarCantidad(float cantidad) throws Exception {
		if(producto instanceof ProductoEmpaquetado && cantidad != Math.ceil(cantidad))
			throw new Exception("La cantidad debe ser entera");
		producto.costoProductos(this.cantidad+cantidad);
		this.cantidad += cantidad;
    }

	public void reducirCantidadEnLotes() throws Exception {
		costo = producto.costoProductos(cantidad);
		producto.reducirCantidad(cantidad);
	}
}
