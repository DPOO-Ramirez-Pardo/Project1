package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Categoria;
import uniandes.dpoo.proyecto1.modelo.Cliente;
import uniandes.dpoo.proyecto1.modelo.Producto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public abstract class ProcesamientoSupermercado {
    protected HashMap<Integer, Producto> productosPorCodigo;
    protected TreeMap<String, Producto> productosPorNombre;
    protected TreeMap<String, Categoria> categorias;
    protected HashMap<Integer, Cliente> clientesPorCedula;
    protected ManejadorArchivos manejadorArchivos;

    public ProcesamientoSupermercado(){
        try {
            manejadorArchivos = new ManejadorArchivos("data/clientes.txt","data/categorias.txt",
                    "data/productos.txt", "data/lotes.txt", "data/recibos.txt");
            manejadorArchivos.guardarArchivos();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
