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

	private static void registrarClienteEnSistemaPuntos() {
		Scanner myObj = new Scanner(System.in);
		System.out.println("Ingrese la cédula del Cliente: ");
		String opcion = myObj.nextLine();

	}

	private static void agregarCantidadProducto() {
		Scanner scanner = new Scanner(System.in);
		if (!agregarProducto(scanner)) return;
		if (!agregarCantidad(scanner)) return;
		System.out.println("El producto fue añadido exitosamente.");
	}

	private static boolean agregarCantidad(Scanner scanner) {
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
				if (e.getMessage().equals("No hay pedido en curso.")) return false;
				if (e.getMessage().equals("No hay producto para agregar cantidad.")) return false;
			}
		}
		return true;
	}

	private static boolean agregarProducto(Scanner scanner) {
		boolean continuarCiclo = true;
		while(continuarCiclo){
			continuarCiclo = false;
			System.out.println("Seleccione el producto que desea agregar a su pedido:\n");
			String productoAgregado = scanner.nextLine();
			try{
				pos.agregarProductoPorNombre(productoAgregado);
			} catch (Exception e){
				System.out.println(e.getMessage());
				if (e.getMessage().equals("No hay pedido en curso.")) return false;
				else if (!e.getMessage().equals("Ya hay un producto siendo agregado.")) continuarCiclo = true;
			}
		}
		return true;
	}

	private static void eliminarProducto() {
		Scanner myObj = new Scanner(System.in);
		System.out.println("Seleccione el producto que desea eliminar a su pedido:\n");
		String productoEliminado = myObj.nextLine();
		pos.eliminarProducto();
		System.out.println("Se eliminó al pedido el elemento: " + productoEliminado);
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

