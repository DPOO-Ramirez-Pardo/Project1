package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Categoria;
import uniandes.dpoo.proyecto1.modelo.Cliente;
import uniandes.dpoo.proyecto1.modelo.Producto;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class ProcesamientoSupermercado {
    protected ArrayList<Producto> productos;
    protected ArrayList<Categoria> categorias;
    protected ArrayList<Cliente> clientes;
    protected ManejadorArchivos manejadorArchivos;

    public ProcesamientoSupermercado(){
        try {
            manejadorArchivos = new ManejadorArchivos("clientes.txt","categorias.txt",
                    "productos.txt", "lotes.txt", "recibos.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
