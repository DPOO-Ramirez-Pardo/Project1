package uniandes.dpoo.proyecto1.consola;

import uniandes.dpoo.proyecto1.modelo.Recibo;
import uniandes.dpoo.proyecto1.procesamiento.Inventario;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Scanner;

public class InventarioConsola {

    static Inventario inventario;

    private static boolean seleccionMenu(int opcion) {
        boolean activo = false;
        switch (opcion) {
            case 1:
                informacionInventarioProducto();
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
                condicionAlmacenamiento();
                activo = true;
                break;
            case 6:
                recibirCarga();
                activo = true;
                break;
            case 7:
                desempenoFinancieroProducto();
                activo = true;
                break;
            case 8:
                eliminarLotesVencidos();
                activo = true;
                break;
            case 9:
                try{inventario.cerrarProcesamiento();} catch (IOException e) {e.printStackTrace();}
                activo = false;
                break;
            default:
                System.out.println(MessageFormat.format("La opción {0} no existe. Escoja una opción del menú.", Integer.toString(opcion)));
                activo = true;
        }
        if (activo) presionarTeclaParaContinuar();
        return activo;
    }

    private static void eliminarLotesVencidos() {
        Scanner scanner = new Scanner(System.in);
        int opcion = leerOpcion(scanner);
        if (opcion == 1) {
            eliminarLotesVencidosPorNombre(scanner);
        } else if (opcion == 2) {
            eliminarLotesVencidosPorCodigo(scanner);
        } else {
            System.out.println("Opción inválida");
        }

    }

    private static void eliminarLotesVencidosPorCodigo(Scanner scanner) {
        System.out.println("Seleccione el producto al cual desea eliminar sus lotes vencidos:\n");
        int codigo = leerEntero(scanner);
        try {
            inventario.eliminarLotesVencidosPorCodigo(codigo);
            System.out.println("Los lotes vencidos han sido eliminados.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarLotesVencidosPorNombre(Scanner scanner) {
        System.out.println("Seleccione el producto al cual desea eliminar sus lotes vencidos:\n");
        String nombre = scanner.nextLine();
        try {
            inventario.eliminarLotesVencidosPorNombre(nombre);
            System.out.println("Los lotes vencidos han sido eliminados.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void informacionInventarioProducto() {
        Scanner scanner = new Scanner(System.in);
        int opcion = leerOpcion(scanner);
        if (opcion == 1) {
            informacionInventarioProductoPorNombre(scanner);
        } else if (opcion == 2) {
            informacionInventarioProductoPorCodigo(scanner);
        } else {
            System.out.println("Opción inválida");
        }
    }

    private static int leerOpcion(Scanner scanner) {
        System.out.println("Introduzca 1. para introducir el producto por nombre y 2. para introducir el producto por código.");
        return leerEntero(scanner);
    }

    private static void informacionInventarioProductoPorNombre(Scanner scanner) {
        System.out.println("Seleccione el producto que desea ver:\n");
        String nombre = scanner.nextLine();
        try {
            System.out.println(inventario.informacionInventarioProductoPorNombre(nombre));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void informacionInventarioProductoPorCodigo(Scanner scanner) {
        System.out.println("Seleccione el producto que desea ver:\n");
        int codigo = leerEntero(scanner);
        try {
            System.out.println(inventario.informacionInventarioProductoPorCodigo(codigo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void desempenoFinancieroProducto() {
        Scanner scanner = new Scanner(System.in);
        int opcion = leerOpcion(scanner);
        if (opcion == 1) {
            desempenoFinancieroProductoPorNombre(scanner);
        } else if (opcion == 2) {
            desempenoFinancieroProductoPorCodigo(scanner);
        } else {
            System.out.println("Opción inválida");
        }
    }

    private static void desempenoFinancieroProductoPorCodigo(Scanner scanner) {
        System.out.println("Seleccione el producto que desea ver:\n");
        int codigo = leerEntero(scanner);
        try {
            System.out.println(inventario.desempenoFinancieroProductoPorCodigo(codigo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void desempenoFinancieroProductoPorNombre(Scanner scanner) {
        System.out.println("Seleccione el producto que desea ver:\n");
        String nombre = scanner.nextLine();
        try {
            System.out.println(inventario.desempenoFinancieroProductoPorNombre(nombre));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void recibirCarga() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca la ruta al archivo de lotes");
        String path = scanner.nextLine();
        try{
            inventario.recibirCarga(path);
            System.out.println("Lotes cargados.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void condicionAlmacenamiento(){
        Scanner scanner = new Scanner(System.in);
        int opcion = leerOpcion(scanner);
        if (opcion == 1) {
            condicionAlmacenamientoPorNombre(scanner);
        } else if (opcion == 2) {
            condicionAlmacenamientoPorCodigo(scanner);
        } else {
            System.out.println("Opción inválida");
        }
    }

    private static void condicionAlmacenamientoPorCodigo(Scanner scanner) {
        System.out.println("Seleccione el producto que desea ver:\n");
        int codigo = leerEntero(scanner);
        try {
            System.out.println(inventario.condicionAlmacenamientoPorCodigo(codigo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void condicionAlmacenamientoPorNombre(Scanner scanner) {
        System.out.println("Seleccione el producto que desea ver:\n");
        String nombre = scanner.nextLine();
        try {
            System.out.println(inventario.condicionAlmacenamientoPorNombre(nombre));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void agregarCategoria() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca el nombre de la nueva categoría:");
        String nombre = scanner.nextLine();
        try {
            inventario.agregarCategoria(nombre);
            System.out.println("Categoría añadida exitosamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void categorizarProducto() {
        Scanner scanner = new Scanner(System.in);
        int opcion = leerOpcion(scanner);
        if (opcion == 1) {
            categorizarProductoPorNombre(scanner);
        } else if (opcion == 2) {
            categorizarProductoPorCodigo(scanner);
        } else {
            System.out.println("Opción inválida");
        }
    }

    private static void categorizarProductoPorCodigo(Scanner scanner) {
        System.out.println("Seleccione el nombre de la categoría:\n");
        String nombreCategoría = scanner.nextLine();
        System.out.println("Seleccione el producto que desea categorizar:\n");
        int codigoProducto = leerEntero(scanner);
        try {
            inventario.categorizarProductoPorCodigo(nombreCategoría,codigoProducto);
            System.out.println("Producto categorizado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void categorizarProductoPorNombre(Scanner scanner) {
        System.out.println("Seleccione el nombre de la categoría:\n");
        String nombreCategoría = scanner.nextLine();
        System.out.println("Seleccione el producto que desea categorizar:\n");
        String nombreProducto = scanner.nextLine();
        try {
            inventario.categorizarProductoPorNombre(nombreCategoría,nombreProducto);
            System.out.println("Producto categorizado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void agregarProducto() {
        System.out.println("Funcionalidad no implementada");

    }

    private static void mostrarInformacionProducto() {
        System.out.println("Información del producto: \n");
        inventario.mostrarInformacionProducto();
    }

    private static void mostrarMenu() {
        System.out.println("""
                Seleccione una opción para continuar:
                1. Mostrar Información del Producto
                2. Agregar un producto
                3. Categorizar un producto
                4. Agregar una categoía
                5. Mostrar condición de almacenamiento del producto
                6. Recibir una carga
                7. Mostrar el desempeño financiero de un producto
                8. Eliminar lotes vencidos
                9. Salir de la aplicación
                """);
    }

    public static void main(String[] args) {
        inventario = new Inventario();
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

    private static int leerEntero(Scanner scanner) {
        int edad = 0;
        while (true) {
            try {
                edad = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida; debe introducir un entero.");
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
                System.out.println(e.getMessage());
            }
        }
        return  edad;
    }

    private static void presionarTeclaParaContinuar(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nPresione alguna tecla para continuar\n");
        scanner.nextLine();
    }
}
