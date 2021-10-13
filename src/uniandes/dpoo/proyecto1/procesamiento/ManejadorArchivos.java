package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class ManejadorArchivos {
    private HashMap<Integer, Producto> productos;
    private HashMap<String, Categoria> categorias;
    private HashMap<Integer, Cliente> clientes;
    private String pathClientes;
    private String pathCategorias;
    private String pathProductos;
    private String pathLotes;
    private String pathRecibos;

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    public HashMap<String, Categoria> getCategorias() {
        return categorias;
    }

    public HashMap<Integer, Cliente> getClientes() {
        return clientes;
    }

    public ManejadorArchivos(String pathClientes, String pathCategorias, String pathProductos,
                             String pathLotes, String pathRecibos) throws FileNotFoundException, ParseException {
        clientes = new HashMap<>();
        categorias = new HashMap<>();
        productos = new HashMap<>();
        this.pathCategorias = pathCategorias;
        this.pathClientes = pathClientes;
        this.pathProductos = pathProductos;
        this.pathLotes = pathLotes;
        this.pathRecibos = pathRecibos;
        cargarClientes(pathClientes);
        cargarProductos(pathProductos);
        cargarCategorias(pathCategorias);
        cargarLotes(pathLotes);
        cargarRecibos(pathRecibos);
    }

    private void cargarProductos(String path) throws FileNotFoundException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()) {
            ArrayList<String> data = getData(scanner);
            String nombre = data.get(0);
            String descripcion = data.get(1);
            int codigo = Integer.parseInt(data.get(2));
            CondicionAlmacenamiento condicionAlmacenamiento = CondicionAlmacenamiento.valueOf(data.get(3));
            float cantidadVendida = Float.parseFloat(data.get(4));
            float dineroAdquirido = Float.parseFloat(data.get(5));
            String unidad = data.get(6);
            añadirProducto(data, nombre, descripcion, codigo, condicionAlmacenamiento, cantidadVendida, dineroAdquirido, unidad);
        }
    }

    private void añadirProducto(ArrayList<String> data, String nombre, String descripcion, int codigo, CondicionAlmacenamiento condicionAlmacenamiento, float cantidadVendida, float dineroAdquirido, String unidad) {
        if (data.size() > 7){
            float peso = Float.parseFloat(data.get(7));
            productos.put(codigo, new ProductoEmpaquetado(nombre, descripcion, codigo, condicionAlmacenamiento,
                    cantidadVendida, dineroAdquirido, unidad, peso));
        } else {
            productos.put(codigo, new ProductoPorPeso(nombre, descripcion, codigo, condicionAlmacenamiento,
                    cantidadVendida, dineroAdquirido, unidad));
        }
    }

    private void cargarCategorias(String path) throws FileNotFoundException {
        Scanner scanner = getScanner(path);
        crearCategorias(scanner);
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            ArrayList<String> data = new ArrayList<>(Arrays.asList(line.split(",")));
            String nombreCategoria = data.get(0);
            int codigoProducto = Integer.parseInt(data.get(1));
            categorias.get(nombreCategoria).añadirProducto(productos.get(codigoProducto));
            productos.get(codigoProducto).añadirCategoria(categorias.get(nombreCategoria));
        }
    }

    private void crearCategorias(Scanner scanner) {
        ArrayList<String> data = getData(scanner);
        for (int i = 0; i < data.size(); i++) {
            categorias.put(data.get(i), new Categoria(data.get(i)));
        }
    }

    private void cargarClientes(String path) throws FileNotFoundException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()){
            ArrayList<String> data = getData(scanner);
            String nombre = data.get(0);
            int cedula = Integer.parseInt(data.get(1));
            int edad = Integer.parseInt(data.get(2));
            float puntos = Float.parseFloat(data.get(3));
            Sexo sexo = Sexo.valueOf(data.get(4));
            SituacionEmpleo situacionEmpleo = SituacionEmpleo.valueOf(data.get(5));
            EstadoCivil estadoCivil = EstadoCivil.valueOf(data.get(6));
            clientes.put(cedula, new Cliente(nombre, cedula, edad, puntos, sexo, situacionEmpleo, estadoCivil));
        }
    }

    private void cargarLotes(String path) throws FileNotFoundException, ParseException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()){
            ArrayList<String> data = getData(scanner);
            int codigo = Integer.parseInt(data.get(0));
            Date fechaLlegada = DateFormat.getDateInstance().parse(data.get(1));
            Date fechaVencimiento = DateFormat.getDateInstance().parse(data.get(2));
            float cantidadInicial = Float.parseFloat(data.get(3));
            float cantidadActual = Float.parseFloat(data.get(4));
            productos.get(codigo).añadirLote(new Lote(fechaLlegada, fechaVencimiento, cantidadInicial, cantidadActual));
        }
    }

    private void cargarRecibos(String path) throws FileNotFoundException, ParseException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()){
            ArrayList<String> data = getData(scanner);
            int cedula = Integer.parseInt(data.get(0));
            Date fecha = DateFormat.getDateInstance().parse(data.get(1));
            ArrayList<CantidadProducto> cantidadesProductos = new ArrayList<>();
            for (int i = 2; i < cantidadesProductos.size(); i+=2) {
                float cantidad = Float.parseFloat(data.get(i));
                Producto producto = productos.get(Integer.parseInt(data.get(i+1)));
                cantidadesProductos.add(new CantidadProducto(cantidad, producto));
            }
            Recibo recibo = new Recibo(fecha, clientes.get(cedula), cantidadesProductos);
            clientes.get(cedula).añadirRecibo(recibo);
        }
    }

    private ArrayList<String> getData(Scanner scanner) {
        String line = scanner.nextLine();
        return new ArrayList<>(Arrays.asList(line.split(",")));
    }

    private Scanner getScanner(String path) throws FileNotFoundException {
        File archivo = new File(path);
        Scanner scanner = new Scanner(archivo);
        return scanner;
    }

    public void guardarArchivos() throws IOException {
        guardarClientes();
        guardarCategorias();
        guardarProductos();
        guardarLotes();
    }

    private void guardarClientes() throws IOException {
        StringBuilder clientesBuilder = new StringBuilder();
        StringBuilder recibosBuilder = new StringBuilder();
        for (Cliente cliente: clientes.values()) {
            clientesBuilder.append(cliente.lineaArchivo()).append('\n');
            for (Recibo recibo: cliente.getRecibos()) {
                recibosBuilder.append(recibo.lineaArchivo()).append('\n');
            }
        }
        writeFile(clientesBuilder, pathClientes);
        writeFile(recibosBuilder, pathRecibos);
    }

    private void writeFile(StringBuilder clientesBuilder, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(clientesBuilder.toString());
        writer.close();
    }

    private void guardarCategorias(){

    }

    private void guardarProductos(){

    }

    private void guardarLotes(){

    }


}
