package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.procesamiento.Inventario;
import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class InventarioInterfaz extends JFrame {
    private Inventario inventario;
    private InventarioMainMenu mainMenu;
    private InventarioInformacionProductoMenu informacionProductoMenu;
    private InventarioAgregarCategoriaMenu inventarioAgregarCategoriaMenu;
    private InventarioEliminarLotesVencidosMenu eliminarLotesVencidosMenu;

    public InventarioInterfaz(){
        setTitle("Inventario");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        inventario = new Inventario();
        mainMenu = new InventarioMainMenu(inventario, this);
        informacionProductoMenu = new InventarioInformacionProductoMenu(inventario, this);
        eliminarLotesVencidosMenu = new InventarioEliminarLotesVencidosMenu(inventario, this);

        add(mainMenu, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String args[]){
        new InventarioInterfaz();
    }

    public void verInformacionProducto() {
        remove(mainMenu);
        add(informacionProductoMenu, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void eliminarLotesVencidos() {
        remove(mainMenu);
        add(eliminarLotesVencidosMenu, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void agregarCategoria() {

    }

    public void recibirCarga() {

    }

    public void volverMainMenu(int back){
        if (back == 0) remove(informacionProductoMenu);
        else if (back == 1) remove(inventarioAgregarCategoriaMenu);
        else if (back == 2) remove(eliminarLotesVencidosMenu);

        add(mainMenu, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void salirDeLaAplicacion() {
        try {
            inventario.cerrarProcesamiento();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
