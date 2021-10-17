package uniandes.dpoo.proyecto1.consola;

import uniandes.dpoo.proyecto1.modelo.Recibo;
import uniandes.dpoo.proyecto1.procesamiento.Inventario;


import java.text.MessageFormat;
import java.util.Scanner;

public class InventarioConsola {

    static Inventario inventario;

    private static boolean seleccionMenu(int opcion){
    	boolean activo = false;
        switch (opcion){
        case 1:
        	mostrarInformacionProducto();
        	activo = true;
        	break;
        case 2:
        	agregarProducto();
        	activo = true;
        	break;
        case 3:
        	categorizarProducto();
        	activo = true;
        	break;
        case 4:
        	agregarCategoria();
        	activo = true;
        	break;
        case 5:
        	Scanner myObj = new Scanner (System.in);
        	System.out.println("Seleccione el producto:\n");
        	String nombreProducto = myObj.nextLine();
        	condicionAlmacenamiento(nombreProducto);
        	activo = true;
        	break;
        case 6:
        	recibirCarga();
        	activo = true;
        	break;
        case 7:
        	desempenoFinancieroProducto();
        	activo = true;
        case 8:
        	informacionInventarioProducto();
        	activo = true;
        case 9:
        	eliminarLotesVencidos();
        	activo = true;
        case 10:
        	activo = true;
        	break;
            default:
            	System.out.println(MessageFormat.format("La opción {0} no existe. Escoja una opción del menú.", Integer.toString(opcion)));
            	activo = true;
        }
        return activo;
    }
    
    private static void eliminarLotesVencidos(){
    	System.out.println("ILotes vencidos eliminados: \n");
    	inventario.eliminarLotesVencidos();
    }

    private static void informacionInventarioProducto(){
    	System.out.println("Información del producto mostrada: \n");
    	inventario.informacionInventarioProducto();

    }

    private static void desempenoFinancieroProducto(){
    	System.out.println("Mostrando desempeño financiero del producto: \n");
    	inventario.desempenoFinancieroProducto();

    }

    private static void recibirCarga(){
    	inventario.recibirCarga();
    	System.out.println("Carga recibida");
    }

    public static String condicionAlmacenamiento(String nombreProducto){
    	System.out.println("El producto se encuentra"+inventario.condicionAlmacenamiento(nombreProducto));
    }

    private static void agregarCategoria(){
    

    }

    private static void categorizarProducto(){
    	System.out.println("Producto categorizado");

    }

    private static void agregarProducto(){
    	Scanner myObj = new Scanner (System.in);
    	System.out.println("Seleccione el producto que desea agregar:\n");
    	String productoAgregado = myObj.nextLine();
    	inventario.agregarProducto();
    	System.out.println("Se agrego al pedido el elemento: "+ productoAgregado);

    }

    private static void mostrarInformacionProducto(){
    	System.out.println("Información del producto: \n");
    	inventario.mostrarInformacionProducto();
    }

    private static void mostrarMenu(){
            System.out.println("""
                    Seleccione una opción para continuar:
                    1. Mostrar Información del Producto
                    2. Agregar un producto
                    3. Categorizar un producto
                    4. Agregar una categoía
                    5. Mostrar condición de almacenamiento del producto
                    6. Recibir una carga
                    7. Mostrar el desempeño financiero de un producto
                    8. Mostrar información del producto en el inventario
                    9. Eliminar lotes vencidos
                    10. Salir de la aplicación
                    """);
    }

    public static void main(String[] args) {
        inventario = new Inventario();
        boolean doCycle = true;
        Scanner input = new Scanner(System.in);
        while(doCycle){
            mostrarMenu();
            String linea = input.nextLine();
            int opcion = 99;
            try{
                opcion = Integer.parseInt(linea);
                doCycle = seleccionMenu(opcion);
            } catch(NumberFormatException e){
                System.out.println("Entrada inválida; debe introducir un entero.");
            }
        }
    }
}
