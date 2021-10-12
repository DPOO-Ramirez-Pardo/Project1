package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;

public class Categoria {
private String nombre;
private ArrayList <Producto> productos;

public Categoria (String nombre) {
	this.nombre = nombre;
}

public String getNombre() {
	return nombre;
}


}
