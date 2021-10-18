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
    private TreeMap<String, Categoria> categorias;
    private HashMap<Integer, Cliente> clientes;
    private String pathClientes;
    private String pathCategorias;
    private String pathProductos;
    private String pathLotes;
    private String pathRecibos;

    public HashMap<Integer, Producto> getProductos() {
        return productos;
    }

    public TreeMap<String, Categoria> getCategorias() {
        return categorias;
    }

    public HashMap<Integer, Cliente> getClientes() {
        return clientes;
    }

    public ManejadorArchivos(String pathClientes, String pathCategorias, String pathProductos,
                             String pathLotes, String pathRecibos) throws FileNotFoundException, ParseException {
        clientes = new HashMap<>();
        categorias = new TreeMap<>();
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
            cargarProducto(data);
        }
    }

    private void cargarProducto(ArrayList<String> data) {
        String nombre = data.get(0);
        String descripcion = data.get(1);
        int codigo = Integer.parseInt(data.get(2));
        CondicionAlmacenamiento condicionAlmacenamiento = CondicionAlmacenamiento.valueOf(data.get(3));
        float cantidadVendida = Float.parseFloat(data.get(4));
        float cantidadDeshechada = Float.parseFloat(data.get(5));
        float dineroAdquirido = Float.parseFloat(data.get(6));
        String unidad = data.get(7);
        if (data.size() > 8){
            float peso = Float.parseFloat(data.get(8));
            productos.put(codigo, new ProductoEmpaquetado(nombre, descripcion, codigo, condicionAlmacenamiento,
                    cantidadVendida, cantidadDeshechada, dineroAdquirido, unidad, peso));
        } else {
            productos.put(codigo, new ProductoPorPeso(nombre, descripcion, codigo, condicionAlmacenamiento,
                    cantidadVendida, cantidadDeshechada, dineroAdquirido, unidad));
        }
    }

    private void cargarCategorias(String path) throws FileNotFoundException {
        Scanner scanner = getScanner(path);
        crearCategorias(scanner);
        String line;
        while (scanner.hasNextLine()) {
            ArrayList<String> data = getData(scanner);
            categorizarProducto(data);
        }
    }

    private void categorizarProducto(ArrayList<String> data) {
        String nombreCategoria = data.get(0);
        int codigoProducto = Integer.parseInt(data.get(1));
        categorias.get(nombreCategoria).añadirProducto(productos.get(codigoProducto));
        productos.get(codigoProducto).añadirCategoria(categorias.get(nombreCategoria));
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
            Cliente cliente = cargarCliente(data);
            clientes.put(cliente.getCedula(), cliente);
        }
    }

    private Cliente cargarCliente(ArrayList<String> data) {
        String nombre = data.get(0);
        int cedula = Integer.parseInt(data.get(1));
        int edad = Integer.parseInt(data.get(2));
        float puntos = Float.parseFloat(data.get(3));
        Sexo sexo = Sexo.valueOf(data.get(4));
        SituacionEmpleo situacionEmpleo = SituacionEmpleo.valueOf(data.get(5));
        EstadoCivil estadoCivil = EstadoCivil.valueOf(data.get(6));
        return new Cliente(nombre, cedula, edad, puntos, sexo, situacionEmpleo, estadoCivil);
    }

    void cargarLotes(String path) throws FileNotFoundException, ParseException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()){
            ArrayList<String> data = getData(scanner);
            int codigo = Integer.parseInt(data.get(0));
            Lote lote = cargarLote(data);
            productos.get(codigo).añadirLote(lote);
        }
    }

    private Lote cargarLote(ArrayList<String> data) throws ParseException {
        Date fechaLlegada = DateFormat.getDateInstance().parse(data.get(1));
        Date fechaVencimiento = DateFormat.getDateInstance().parse(data.get(2));
        float cantidadInicial = Float.parseFloat(data.get(3));
        float cantidadActual = Float.parseFloat(data.get(4));
        float precioUnidadAdquisicion = Float.parseFloat(data.get(5));
        float precioVentaAlPublico = Float.parseFloat(data.get(6));
        return new Lote(fechaLlegada, fechaVencimiento, cantidadInicial, cantidadActual, precioUnidadAdquisicion, precioVentaAlPublico);
    }

    private void cargarRecibos(String path) throws FileNotFoundException, ParseException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()){
            ArrayList<String> data = getData(scanner);
            Recibo recibo = cargarRecibo(data);
            clientes.get(recibo.getCliente().getCedula()).añadirRecibo(recibo);
        }
    }

    private Recibo cargarRecibo(ArrayList<String> data) throws ParseException {
        int cedula = Integer.parseInt(data.get(0));
        Date fecha = DateFormat.getDateInstance().parse(data.get(1));
        ArrayList<CantidadProducto> cantidadesProductos = new ArrayList<>();
        for (int i = 2; i < data.size(); i+=2) {
            float cantidad = Float.parseFloat(data.get(i));
            Producto producto = productos.get(Integer.parseInt(data.get(i+1)));
            try {cantidadesProductos.add(new CantidadProducto(cantidad, producto));} catch (Exception e) {}
        }
        return new Recibo(fecha, clientes.get(cedula), cantidadesProductos);
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

    private void writeFile(StringBuilder builder, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write(builder.toString());
        writer.close();
    }

    private void guardarCategorias() throws IOException {
        StringBuilder builder = new StringBuilder();
        añadirLineaNombresCategorias(builder);
        añadirLineasCategoriasProductos(builder);
        writeFile(builder, pathCategorias);
    }

    private void añadirLineasCategoriasProductos(StringBuilder builder) {
        for (Categoria categoria: categorias.values()){
            for (Producto producto: categoria.getProductos()){
                builder.append(categoria.getNombre()).append(',').append(producto.getCodigo()).append('\n');
            }
        }
    }

    private void añadirLineaNombresCategorias(StringBuilder builder) {
        boolean first = true;
        for (String key: categorias.keySet()){
            if (first){
                first = false;
                builder.append(key);
            } else builder.append(',').append(key);
        }
        builder.append('\n');
    }

    private void guardarProductos() throws IOException {
        StringBuilder productosBuilder = new StringBuilder();
        StringBuilder lotesBuilder = new StringBuilder();
        for(Producto producto: productos.values()){
            productosBuilder.append(producto.lineaArchivo()).append('\n');
            for (Lote lote: producto.getLotes()){
                lotesBuilder.append(producto.getCodigo()).append(',').append(lote.lineaArchivo()).append('\n');
            }
        }
        writeFile(productosBuilder, pathProductos);
        writeFile(lotesBuilder, pathLotes);
    }


}
