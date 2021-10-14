package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Categoria;
import uniandes.dpoo.proyecto1.modelo.Cliente;
import uniandes.dpoo.proyecto1.modelo.Producto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class ProcesamientoSupermercado {
    protected ArrayList<Producto> productos;
    protected ArrayList<Categoria> categorias;
    protected ArrayList<Cliente> clientes;
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
