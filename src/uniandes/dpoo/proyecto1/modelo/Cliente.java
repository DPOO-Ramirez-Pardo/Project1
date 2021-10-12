package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;

public class Cliente {
	private String nombre;
	private int cedula;
	private int edad;
	private enum estadoCivil {
		Casado, Soltero, Otro
	}
	private enum sexo {
		Masculino, Femenino, Otro
	}
	private enum situacionLaboral {
		Empleado, Desempleado
	}
	private float puntos;
	private ArrayList <Recibo> recibos;
	
	public Cliente (String nombre, int cedula, int edad, float puntos) {
		this.nombre = nombre;
		this.cedula = cedula;
		this.edad = edad;
		this.puntos = puntos;
	}

	public String getNombre() {
		return nombre;
	}

	public int getCedula() {
		return cedula;
	}

	public int getEdad() {
		return edad;
	}

	public float getPuntos() {
		return puntos;
	}
	





}


