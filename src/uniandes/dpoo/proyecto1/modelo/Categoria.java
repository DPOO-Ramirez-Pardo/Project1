package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;

public class Categoria {
	private String nombre;

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	private ArrayList <Producto> productos;

	public Categoria (String nombre) {
		this.nombre = nombre;
		this.productos = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void añadirProducto(Producto producto){
		productos.add(producto);
	}

}
