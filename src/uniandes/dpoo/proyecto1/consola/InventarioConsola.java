package uniandes.dpoo.proyecto1.consola;

import uniandes.dpoo.proyecto1.modelo.Recibo;
import uniandes.dpoo.proyecto1.procesamiento.Inventario;

import java.text.MessageFormat;
import java.util.Scanner;

public class InventarioConsola {

    static Inventario inventario;

    private static boolean seleccionMenu(int opcion){
        switch (opcion){
            default -> System.out.println(MessageFormat.format("La opción {0} no existe. Escoja una opción del menú.", Integer.toString(opcion)));
        }
        return true;
    }

    private static void mostrarMenu(){

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
