package uniandes.dpoo.proyecto1.consola;
import uniandes.dpoo.proyecto1.procesamiento.*;
import uniandes.dpoo.proyecto.modelo.*;

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
				pos.cerrarProcesamiento();
				activo = false;
			default:
				System.out.println(MessageFormat.format("La opción {0} no existe. Escoja una opción del menú.", Integer.toString(opcion)));
				activo = true;
		}
		return true;
	}

	private static void generarRecibo() {
		Scanner myObj = new Scanner(System.in);
		System.out.println("¿Desea generar un recibo?:\n");
		String opcion = myObj.nextLine();
		switch (opcion) {
			case "si":
				pos.generarRecibo();
				System.out.println("Recibo impreso, compra exitosa");
			case "no":
				System.out.println("Recibo no impreso, compra exitosa");
			default:
				System.out.println("Por favor, inserte si o no");
		}
	}
	
	private static void cancelarPedido(Scanner scanner) {
		boolean continuarCiclo = true;
		System.out.println("¿Está seguro que desea cancelar el pedido?: \n");
		String respuesta = scanner.nextInt();
		while (continuarCiclo) {
			if (respuesta == "si") {
				continuarCiclo = false;
				pos.cancelarPedido();
			}
			else if (respuesta == "no") {
				continuarCiclo = false;
			}
			else {
				continuarCiclo = true;
			}
		}
	}
	

	private static void registrarClienteEnSistemaPuntos() {
		Scanner myObj = new Scanner(System.in);
		System.out.println("Ingrese su cédula:\n ");
		int cedula = myObj.nextInt();
		if (pos.existeCliente) {
			System.out.println("Su nombre ya se encuentra en el sistema");
		}
		else {
			System.out.println("Ingrese su nombre:\n");
			String nombre = myObj.nextLine();
			System.out.println("Ingrese su edad:\n");
			int edad = myObj.nextInt();
			System.out.println("¿Cuál es su estado civil: casado, soltero, u otro?: \n");
			enum estadoCivil = 
			System.out.println("¿Cuál es su genero?: \n");
			enum genero = Sexo.valueOf();
			System.out.println("¿Cuál es su situación laboral: empleado, desempleado, independiente o estudiante?: \n");
			enum situacionEmpleo = myObj.nextLine();
			int puntos = 0;
			pos.registrarClienteEnSistemaPuntos;
			
		}
	

	}

	private static void agregarCantidadProducto() {
		Scanner scanner = new Scanner(System.in);
		if (!agregarProducto(scanner)) return;
		if (!agregarCantidad(scanner)) return;
		System.out.println("El producto fue añadido exitosamente.");
	}
	
	

	private static void agregarCantidad(Scanner scanner) {
		boolean continuarCiclo = true;
		while (continuarCiclo){
			continuarCiclo = false;
			try{
				if (pos.actualProductoEsEmpaquetado()){
					System.out.println("Seleccione la cantidad de paquetes que desea agregar a su pedido:\n");
					int cantidad = Integer.parseInt(scanner.nextLine());
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
				if (e instanceof ParseException) continuarCiclo = true;
				if (e.getMessage().equals("No hay pedido en curso.")) continuarCiclo = false;
				if (e.getMessage().equals("No hay producto para agregar cantidad.")) continuarCiclo = false;
			}
		}
		continuarMenu(scanner);
	}

	private static void agregarProducto(Scanner scanner) {
		boolean continuarCiclo = true;
		
		System.out.println("Seleccione 1 si desea agregar el producto por nombre, o 2 si desea agregarlo por código de barras: \n");
		String opcion = scanner.nextInt();
		switch (opcion) {
		case 1:
			while(continuarCiclo){
				continuarCiclo = false;
				System.out.println("Inserte el producto que desea agregar a su pedido:\n");
				String productoAgregado = scanner.nextLine();
				try{
					pos.agregarProductoPorNombre(productoAgregado);
					pos.generarRecibo();
				} catch (Exception e){
					System.out.println(e.getMessage());
					if (e.getMessage().equals("No hay pedido en curso.")) return false;
					else if (!e.getMessage().equals("Ya hay un producto siendo agregado.")) continuarCiclo = true;
				}
			}
			return true;
		case 2:
			while(continuarCiclo){
				continuarCiclo = false;
				System.out.println("Inserte el producto que desea agregar a su pedido:\n");
				int productoAgregado = scanner.nextInt();
				try{
					pos.agregarProductoPorNombre(productoAgregado);
					pos.generarRecibo();
				} catch (Exception e){
					System.out.println(e.getMessage());
					if (e.getMessage().equals("No hay pedido en curso.")) return false;
					else if (!e.getMessage().equals("Ya hay un producto siendo agregado.")) continuarCiclo = true;
				}
			}
		default:
			Exception e;
			System.out.println(e.getMessage());
			if (e.getMessage().equals("No hay pedido en curso.")) return false;
			else if (!e.getMessage().equals("Ya hay un producto siendo agregado.")) continuarCiclo = true;
		}
		continuarMenu(scanner);
	}

	private static void eliminarProducto(Scanner scanner) {
			boolean continuarCiclo = true;
			
			System.out.println("Seleccione 1 si desea eliminar el producto por nombre, o 2 si desea eliminarlo por código de barras: \n");
			String opcion = scanner.nextInt();
			switch (opcion) {
			case 1:
				while(continuarCiclo){
					continuarCiclo = false;
					System.out.println("Inserte el producto que desea eliminar a su pedido:\n");
					String productoEliminado = scanner.nextLine();
					try{
						pos.agregarProductoPorNombre(productoEliminado);
						pos.generarRecibo();
					} catch (Exception e){
						System.out.println(e.getMessage());
						if (e.getMessage().equals("No hay pedido en curso.")) return false;
						else if (!e.getMessage().equals("Ya hay un producto siendo agregado.")) continuarCiclo = true;
					}
				}
				return true;
			case 2:
				while(continuarCiclo){
					continuarCiclo = false;
					System.out.println("Inserte el producto que desea eliminar a su pedido:\n");
					int productoEliminado = scanner.nextInt();
					try{
						pos.agregarProductoPorNombre(productoEliminado);
						pos.generarRecibo();
					} catch (Exception e){
						System.out.println(e.getMessage());
						if (e.getMessage().equals("No hay pedido en curso.")) continuarCiclo = false;
						else if (!e.getMessage().equals("Ya hay un producto siendo agregado.")) continuarCiclo = true;
					}
				}
			default:
				Exception e;
				System.out.println(e.getMessage());
				if (e.getMessage().equals("No hay pedido en curso.")) continuarCiclo = false;
				else if (!e.getMessage().equals("Ya hay un producto siendo agregado.")) continuarCiclo = true;
			}
			continuarMenu(scanner);
		}

	private static void iniciarPedido() {
		try{
			pos.iniciarPedido();
			System.out.println("Pedido iniciado...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			continuarMenu(scanner);
		}
	}
	
	private static void continuarMenu(Scanner scanner) {
		System.out.println("¿Desea continuar en el menú? y/n: ");
		String opcion = scanner.nextLine();
		switch (opcion) {
		case "y":
			main();
		case "n":
			break;
		
		default:
			System.out.println("Por favor, seleccione y o n");
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

