package uniandes.dpoo.proyecto1.consola;
import uniandes.dpoo.proyecto1.procesamiento.*;

import java.io.IOException;
import java.text.MessageFormat;

import uniandes.dpoo.proyecto1.modelo.*;

import java.text.ParseException;
import java.util.Scanner;

public class POSConsola {
	static POS pos;

	private static boolean seleccionMenu(int opcion) {
		boolean activo = false;
		switch (opcion) {
			case 1:
				iniciarPedido();
				activo = true;
				break;
			case 2:
				agregarCantidadProducto();
				activo = true;
				break;
			case 3:
				eliminarProducto();
				activo = true;
				break;
			case 4:
				registrarClienteEnSistemaPuntos();
				activo = true;
				break;
			case 5:
				añadirTitular();
				activo = true;
				break;
			case 6:
				activo = true;
				break;
			case 7:
				generarRecibo();
				activo = true;
				break;
			case 8:
				try{pos.cerrarProcesamiento();} catch (IOException e) {e.printStackTrace();}
				activo = false;
			default:
				System.out.println(MessageFormat.format("La opción {0} no existe. Escoja una opción del menú.", Integer.toString(opcion)));
				activo = true;
		}
		return activo;
	}

	private static void eliminarProducto() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Introduzca 1. para introducir el producto por nombre y 2. para introducir el producto por código.");
		int opcion = leerEntero(scanner);
		if(opcion == 1){
			eliminarProductoPorNombre(scanner);
		} else if (opcion == 2){
			eliminarProductoPorCodigo(scanner);
		} else {
			System.out.println("Opción inválida"); return;
		}
	}

	private static void añadirTitular() {
		System.out.println("Introduzca la cédula del cliente:");
		int cedula = leerEntero(new Scanner(System.in));
		if (pos.existeCliente(cedula)){
			pos.añadirTitular(cedula);
			System.out.println("Cliente añadido exitosamente al recibo");
		} else {
			System.out.println("No existe cliente con esa cédula.");
		}
	}

	private static void generarRecibo() {
		try {
			System.out.println(pos.cerrarRecibo());
			System.out.println("Recibo impreso, compra exitosa");
		} catch (Exception e) {e.printStackTrace();}
	}

	private static void registrarClienteEnSistemaPuntos() {
		Scanner scanner = new Scanner(System.in);
		int cedula = leerCedula(scanner);
		if (cedula == 0) return;
		System.out.println("Introduzca el nombre del Cliente:");
		String nombre = scanner.nextLine();
		System.out.println("Introduzca la edad del Cliente:");
		int edad = leerEntero(scanner);
		float puntos = 0;
		Sexo sexo = leerSexo(scanner);
		SituacionEmpleo situacionEmpleo = leerSituacionEmpleo(scanner);
		EstadoCivil estadoCivil = leerEstadoCivil(scanner);
		try {
			pos.registrarClienteEnSistemaPuntos(nombre, cedula, edad, puntos, sexo, situacionEmpleo, estadoCivil);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int leerEntero(Scanner scanner) {
		int edad = 0;
		while (true) {
			try {
				edad = Integer.parseInt(scanner.nextLine());
				break;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return edad;
	}

	private static float leerFlotante(Scanner scanner) {
		float edad = 0;
		while (true) {
			try {
				edad = Float.parseFloat(scanner.nextLine());
				break;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return edad;
	}

	private static int leerCedula(Scanner scanner) {
		System.out.println("La cédula del Cliente:");
		int cedula = 0;
		cedula = leerEntero(scanner);
		if (pos.existeCliente(cedula)){
			System.out.println("El cliente ya existe.");
			cedula = 0;
		}
		return cedula;
	}

	private static Sexo leerSexo(Scanner scanner) {
		System.out.println("Introduzca su Sexo");
		Sexo sexo = Sexo.Masculino;
		while(true){
			try{
				sexo = Sexo.valueOf(scanner.nextLine());
				break;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return sexo;
	}

	private static SituacionEmpleo leerSituacionEmpleo(Scanner scanner) {
		System.out.println("Introduzca su Situación de Empleo");
		SituacionEmpleo situacionEmpleo = SituacionEmpleo.Desempleado;
		while(true){
			try{
				situacionEmpleo = SituacionEmpleo.valueOf(scanner.nextLine());
				break;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return situacionEmpleo;
	}

	private static EstadoCivil leerEstadoCivil(Scanner scanner) {
		System.out.println("Introduzca el Estado Civil");
		EstadoCivil estadoCivil = EstadoCivil.Soltero;
		while(true){
			try{
				estadoCivil = EstadoCivil.valueOf(scanner.nextLine());
				break;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		return estadoCivil;
	}

	private static void agregarCantidadProducto() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Introduzca 1. para introducir el producto por nombre y 2. para introducir el producto por código.");
		int opcion = leerEntero(scanner);
		if(opcion == 1){
			if (!agregarProductoPorNombre(scanner)) return;
		} else if (opcion == 2){
			if (!agregarProductoPorCodigo(scanner)) return;
		} else {
			System.out.println("Opción inválida"); return;
		}
		if (!agregarCantidad(scanner)) return;
		System.out.println("El producto fue añadido exitosamente.");
	}

	private static boolean agregarCantidad(Scanner scanner) {
	try{
		if (pos.actualProductoEsEmpaquetado()){
			System.out.println("Seleccione la cantidad de paquetes que desea agregar a su pedido:\n");
			int cantidad = leerEntero(scanner);
			pos.agregarCantidadProducto(cantidad);
		} else {
			System.out.println(
					MessageFormat.format("Seleccione la cantidad de {} que desea agregar a su pedido:\n",
							pos.unidadActualProducto()));
			float cantidad = Float.parseFloat(scanner.nextLine());
			pos.agregarCantidadProducto(cantidad);
		}
	} catch (Exception e){
		System.out.println(e.getMessage());
		pos.cancelarProducto();
	}
		return true;
	}

	private static boolean agregarProductoPorNombre(Scanner scanner) {
		System.out.println("Seleccione el producto que desea agregar a su pedido:\n");
		String productoAgregado = scanner.nextLine();
		try{
			pos.agregarProductoPorNombre(productoAgregado);
			return true;
		} catch (Exception e){
			System.out.println(e.getMessage());
			return false;
		}
	}

	private static boolean agregarProductoPorCodigo(Scanner scanner) {
		System.out.println("Seleccione el producto que desea agregar a su pedido:\n");
		int codigo = leerEntero(scanner);
		try{
			pos.agregarProductoPorCodigo(codigo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void eliminarProductoPorNombre(Scanner scanner) {
		System.out.println("Seleccione el producto que desea eliminar de su pedido:\n");
		String elim = scanner.nextLine();
		try{
			pos.eliminarProductoPorNombre(elim);
			System.out.println("El producto ha sido eliminado.");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	private static void eliminarProductoPorCodigo(Scanner scanner) {
		System.out.println("Seleccione el producto que desea eliminar de su pedido:\n");
		int codigo = leerEntero(scanner);
		try{
			pos.eliminarProductoPorCodigo(codigo);
			System.out.println("El producto ha sido eliminado.");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	private static void iniciarPedido() {
		try{
			pos.iniciarPedido();
			System.out.println("Pedido iniciado...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void mostrarMenu() {
		System.out.println("""
				Seleccione una opción para continuar:
				1. Iniciar Nuevo Pedido
				2. Agregar Producto al Pedido
				3. Eliminar Producto del Pedido
				4. Registrar Cliente en Sistema Puntos
				5. Añadir Cliente como titular del Pedido
				6. Cancelar Pedido
				7. Generar Recibo
				8. Cerrar Programa
				""");
	}

	public static void main(String[] args) {
		pos = new POS();
		boolean doCycle = true;
		Scanner input = new Scanner(System.in);
		while (doCycle) {
			mostrarMenu();
			String linea = input.nextLine();
			int opcion = 99;
			try {
				opcion = Integer.parseInt(linea);
				doCycle = seleccionMenu(opcion);
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida; debe introducir un entero.");
			}
		}
	}
}

