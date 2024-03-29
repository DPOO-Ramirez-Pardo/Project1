package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.exceptions.FechasCantidadesInconsistentesException;
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

    public ArrayList<Recibo> getRecibosSinCedula() {
        return recibosSinCedula;
    }

    private ArrayList<Recibo> recibosSinCedula;
    private String pathClientes;
    private String pathCategorias;
    private String pathProductos;
    private String pathLotes;
    private String pathRecibos;
    private String pathComportamientos;

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
                             String pathLotes, String pathRecibos, String pathComportamientos) throws FileNotFoundException, ParseException {
        clientes = new HashMap<>();
        categorias = new TreeMap<>();
        productos = new HashMap<>();
        this.pathCategorias = pathCategorias;
        this.pathClientes = pathClientes;
        this.pathProductos = pathProductos;
        this.pathLotes = pathLotes;
        this.pathRecibos = pathRecibos;
        this.pathComportamientos = pathComportamientos;
        recibosSinCedula = new ArrayList<>();
        cargarClientes(pathClientes);
        cargarProductos(pathProductos);
        cargarCategorias(pathCategorias);
        cargarLotes(pathLotes);
        cargarRecibos(pathRecibos);
        cargarComportamientoProductos(pathComportamientos);
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
        String pathImagen = data.get(2);
        int codigo = Integer.parseInt(data.get(3));
        CondicionAlmacenamiento condicionAlmacenamiento = CondicionAlmacenamiento.valueOf(data.get(4));
        float cantidadVendida = Float.parseFloat(data.get(5));
        float cantidadDeshechada = Float.parseFloat(data.get(6));
        float dineroAdquirido = Float.parseFloat(data.get(7));
        String unidad = data.get(8);
        if (data.size() > 9){
            float peso = Float.parseFloat(data.get(9));
            productos.put(codigo, new ProductoEmpaquetado(nombre, descripcion, pathImagen, codigo, condicionAlmacenamiento,
                    cantidadVendida, cantidadDeshechada, dineroAdquirido, unidad, peso));
        } else {
            productos.put(codigo, new ProductoPorPeso(nombre, descripcion, pathImagen, codigo, condicionAlmacenamiento,
                    cantidadVendida, cantidadDeshechada, dineroAdquirido, unidad));
        }
    }

    private void cargarCategorias(String path) throws FileNotFoundException {
        Scanner scanner = getScanner(path);
        crearCategorias(scanner);
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
        int puntos = Integer.parseInt(data.get(3));
        Sexo sexo = Sexo.valueOf(data.get(4));
        SituacionEmpleo situacionEmpleo = SituacionEmpleo.valueOf(data.get(5));
        EstadoCivil estadoCivil = EstadoCivil.valueOf(data.get(6));
        return new Cliente(nombre, cedula, edad, puntos, sexo, situacionEmpleo, estadoCivil);
    }

    private void cargarLotes(String path) throws FileNotFoundException, ParseException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()){
            ArrayList<String> data = getData(scanner);
            int codigo = Integer.parseInt(data.get(0));
            Lote lote = cargarLote(data);
            productos.get(codigo).añadirLote(lote);
        }
    }

    public void cargarLotesPorPrimeraVez(String path) throws FileNotFoundException, ParseException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()){
            ArrayList<String> data = getData(scanner);
            int codigo = Integer.parseInt(data.get(0));
            Lote lote = cargarLotePorPrimeraVez(data);
            productos.get(codigo).añadirLote(lote);
        }
    }

    private Lote cargarLotePorPrimeraVez(ArrayList<String> data) throws ParseException {
        Date fechaLlegada = Calendar.getInstance().getTime();
        Date fechaVencimiento = DateFormat.getDateInstance().parse(data.get(1));
        float cantidadInicial = Float.parseFloat(data.get(2));
        float cantidadActual = Float.parseFloat(data.get(2));
        float precioUnidadAdquisicion = Float.parseFloat(data.get(3));
        float precioVentaAlPublico = Float.parseFloat(data.get(4));
        return new Lote(fechaLlegada, fechaVencimiento, cantidadInicial, cantidadActual, precioUnidadAdquisicion, precioVentaAlPublico);
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
            if (recibo.getCliente() == null) recibosSinCedula.add(recibo);
            else clientes.get(recibo.getCliente().getCedula()).añadirRecibo(recibo);
        }
    }

    private Recibo cargarRecibo(ArrayList<String> data) throws ParseException {
        int cedula = Integer.parseInt(data.get(0));
        Date fecha = DateFormat.getDateInstance().parse(data.get(1));
        int puntosAcumulados = Integer.parseInt(data.get(2));
        int puntosRedimidos = Integer.parseInt(data.get(3));
        ArrayList<CantidadProducto> cantidadesProductos = new ArrayList<>();
        for (int i = 4; i < data.size()-1; i+=3) {
            float cantidad = Float.parseFloat(data.get(i));
            Producto producto = productos.get(Integer.parseInt(data.get(i+1)));
            float costo = Float.parseFloat(data.get(i+2));
            try {cantidadesProductos.add(new CantidadProducto(cantidad, producto, costo));} catch (Exception e) {}
        }
        float subtotal = Float.parseFloat(data.get(data.size()-1));
        if (cedula == 0) return new Recibo(fecha, null, cantidadesProductos,
                subtotal, puntosAcumulados, puntosRedimidos);
        else return new Recibo(fecha, clientes.get(cedula), cantidadesProductos,
                subtotal, puntosAcumulados, puntosRedimidos);
    }

    private void cargarComportamientoProductos(String path) throws FileNotFoundException, ParseException {
        Scanner scanner = getScanner(path);
        while (scanner.hasNextLine()){
            ArrayList<String> data = getData(scanner);
            cargarComportamientoProducto(data);
        }
    }

    private void cargarComportamientoProducto(ArrayList<String> data) throws ParseException {
        int codigo = Integer.parseInt(data.get(0));
        ArrayList<Date> fechas = new ArrayList<>();
        ArrayList<Float> cantidades = new ArrayList<>();
        for (int i = 1; i < data.size(); i+= 2) {
            fechas.add(DateFormat.getDateInstance().parse(data.get(i)));
            cantidades.add(Float.parseFloat(data.get(i+1)));
        }
        try {
            productos.get(codigo).setComportamiento(new ComportamientoProducto(fechas, cantidades, productos.get(codigo)));
        } catch (FechasCantidadesInconsistentesException e) {
            e.printStackTrace();
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
        guardarRecibosSinCedula(recibosBuilder);
        writeFile(clientesBuilder, pathClientes);
        writeFile(recibosBuilder, pathRecibos);
    }

    private void guardarRecibosSinCedula(StringBuilder builder){
        for (Recibo recibo: recibosSinCedula){
            builder.append(recibo.lineaArchivo()).append('\n');
        }
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
        StringBuilder comportamientosBuilder = new StringBuilder();
        for(Producto producto: productos.values()){
            productosBuilder.append(producto.lineaArchivo()).append('\n');
            for (Lote lote: producto.getLotes()){
                lotesBuilder.append(producto.getCodigo()).append(',').append(lote.lineaArchivo()).append('\n');
            }
            ComportamientoProducto comportamientoProducto = producto.getComportamientoProducto();
            if (comportamientoProducto == null){
                try {
                    producto.setComportamiento(new ComportamientoProducto(new ArrayList<>(), new ArrayList<>(), producto));
                } catch (FechasCantidadesInconsistentesException e) {
                    e.printStackTrace();
                }
            }
            comportamientosBuilder.append(producto.getComportamientoProducto().lineaArchivo()).append('\n');
        }
        writeFile(productosBuilder, pathProductos);
        writeFile(lotesBuilder, pathLotes);
        writeFile(comportamientosBuilder, pathComportamientos);
    }


}
