package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.procesamiento.Inventario;
import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class InventarioInterfaz extends JFrame implements WindowListener {
    private Inventario inventario;
    private InventarioMainMenu mainMenu;
    private InventarioInformacionProductoMenu informacionProductoMenu;
    private InventarioEliminarLotesVencidosMenu eliminarLotesVencidosMenu;

    public InventarioInterfaz(){
        setTitle("Inventario");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
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
        String nombreCategoria = (String) JOptionPane.showInputDialog(this,
                "Introduzca la categoría la cual quiere agregar:","Agregar Categoria",
                JOptionPane.PLAIN_MESSAGE, null, null, "");
        if(nombreCategoria != null){
            try {
                inventario.agregarCategoria(nombreCategoria);
                JOptionPane.showMessageDialog(this, "Se agregó la categoría exitosamente.",
                        "¡Éxito!", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(),
                        "Exception!", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    public void recibirCarga() {
        FileDialog fileDialog = new FileDialog(this, "Imagen", FileDialog.LOAD);
        fileDialog.setVisible(true);
        String path = fileDialog.getDirectory() + fileDialog.getFile();
        if (path != null){
            try {
                inventario.recibirCarga(path);
                JOptionPane.showMessageDialog(this, "Se cargaron los lotes exitosamente.",
                        "¡Éxito!", JOptionPane.PLAIN_MESSAGE);
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(),
                        "Exception!", JOptionPane.PLAIN_MESSAGE);
            } catch (ParseException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(),
                        "Exception!", JOptionPane.PLAIN_MESSAGE);
            }
        }

    }

    public void volverMainMenu(int back){
        if (back == 0) remove(informacionProductoMenu);
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

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        salirDeLaAplicacion();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
