package uniandes.dpoo.proyecto1.modelo;
import java.util.ArrayList;

public class Cliente {
	private String nombre;
	private int cedula;
	private int edad;
	private int puntos;
	private Sexo sexo;
	private EstadoCivil estadoCivil;
	private SituacionEmpleo situacionEmpleo;
	private ArrayList <Recibo> recibos;
	
	public Cliente (String nombre, int cedula, int edad, int puntos,
					Sexo sexo, SituacionEmpleo situacionEmpleo, EstadoCivil estadoCivil) {
		this.nombre = nombre;
		this.cedula = cedula;
		this.edad = edad;
		this.puntos = puntos;
		this.sexo = sexo;
		this.situacionEmpleo = situacionEmpleo;
		this.estadoCivil = estadoCivil;
		this.recibos = new ArrayList<>();
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

	public int getPuntos() {
		return puntos;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public SituacionEmpleo getSituacionEmpleo() {
		return situacionEmpleo;
	}

	public void añadirRecibo(Recibo recibo){
		recibos.add(recibo);
	}

	/**
	 *
	 * @return El texto de la línea que corresponde a la información del cliente en clientes.txt.
	 */
	public String lineaArchivo(){
		return nombre + "," + String.valueOf(cedula) + "," + String.valueOf(edad) + "," +
				String.valueOf(puntos) + "," + sexo.toString() + "," +
				situacionEmpleo.toString() + "," + estadoCivil.toString();
	}

	public ArrayList<Recibo> getRecibos() {
		return recibos;
	}

	public void añadirPuntos(int puntos) {
		this.puntos += puntos;
	}
}


