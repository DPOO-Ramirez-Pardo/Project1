package uniandes.dpoo.proyecto1.consola;
import uniandes.dpoo.proyecto1.procesamiento.*;

import java.io.IOException;
import java.text.MessageFormat;

import uniandes.dpoo.proyecto1.modelo.*;
import java.util.Scanner;

public class POSConsola {
	POS pos;
	private static boolean seleccionMenu(int opcion){
    	boolean activo = false;
        switch (opcion){
        case 1:
        	iniciarPedido();
        	activo = true;
        	break;
        case 2:
        	agregarProducto();
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
            default:
            	System.out.println(MessageFormat.format("La opción {0} no existe. Escoja una opción del menú.", Integer.toString(opcion)));
            	activo = true;
        }
        return true;
    }

	private void generarRecibo(){
    	Scanner myObj = new Scanner (System.in);
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
    }}

    private void registrarClienteEnSistemaPuntos(){
    	Scanner myObj = new Scanner (System.in);
    	System.out.println("Se encuentra registrado en el sistema de puntos?:\n");
    	String opcion = myObj.nextLine();
    	switch (opcion) {
    	case "si":
    		System.out.println("Cliente ingresado en el sistema de puntos");
    	case "no":
    		pos.registrarClienteEnSistemaPuntos();
    		System.out.println("Cliente ingresado en el sistema de puntos");
    	default:
    		System.out.println("Por favor, inserte si o no");
    	}
    }

    private void agregarProducto(){
    	Scanner myObj = new Scanner (System.in);
    	System.out.println("Seleccione el producto que desea agregar a su pedido:\n");
    	String productoAgregado = myObj.nextLine();
    	pos.agregarProducto();
    	System.out.println("Se agrego al pedido el elemento: "+ productoAgregado);
    }

    private void eliminarProducto(){
    	Scanner myObj = new Scanner (System.in);
    	System.out.println("Seleccione el producto que desea eliminar a su pedido:\n");
    	String productoEliminado = myObj.nextLine();
    	pos.eliminarProducto();
    	System.out.println("Se eliminó al pedido el elemento: "+ productoEliminado);
    }

    private void iniciarPedido(){
    	boolean pedidoEnCurso = pos.tienePedido();
    	if (pedidoEnCurso) {
    		System.out.println("Ya existe un pedido en curso");
    	}
    	else {
    		pos.iniciarPedido();
    		System.out.println("Pedido iniciado...");
    	}
    }

    private void mostrarMenu(){
        System.out.println("""
                Seleccione una opción para continuar:
                1. Iniciar Nuevo Pedido
                2. Agregar Producto al Pedido
                3. Eliminar Producto del Pedido
                4. Registrar Cliente en Sistema Puntos
                5. Añadir Cliente como titular del Pedido
                6. Cancelar Pedido
                7. Generar Recibo
                """);
    }

    public static void main(String[] args) {
    }

