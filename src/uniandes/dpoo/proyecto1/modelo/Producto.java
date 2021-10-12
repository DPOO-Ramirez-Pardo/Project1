package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;

public abstract class Producto {
	private String nombre;
	private int codigo;
	private float precioPorUnidad;
	private String unidad;
	private ArrayList <Lote> lotes;
	private ArrayList <Categoria> categorias;
	

public Producto (String nombre, int codigo, float precioPorUnidad, String unidad) {
	this.nombre = nombre;
	this.codigo = codigo;
	this.precioPorUnidad = precioPorUnidad;
	this.unidad = unidad;
}


public String getNombre() {
	return nombre;
}


public int getCodigo() {
	return codigo;
}


public float getPrecioPorUnidad() {
	return precioPorUnidad;
}


public String getUnidad() {
	return unidad;
	
public String mostrarCondicionAlmacenamiento();{
	return condicionAlmacenamiento;
}

public String mostrarCategorias();{
	return categorias;
}

}












}
