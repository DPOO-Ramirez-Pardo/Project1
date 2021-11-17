package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.procesamiento.Inventario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventarioMainMenu extends JLabel implements ActionListener {
    private Inventario inventario;
    private InventarioInterfaz inventarioInterfaz;
    private JButton verInformacionProductoButton;
    private JButton eliminarLotesVencidosButton;
    private JButton agregarCategoriaButton;
    private JButton recibirCargaButton;
    private JButton salirDeLaAplicacion;

    public InventarioMainMenu(Inventario inventario, InventarioInterfaz inventarioInterfaz){
        this.inventario = inventario;
        this.inventarioInterfaz = inventarioInterfaz;
        verInformacionProductoButton = new JButton("VER PRODUCTO");
        verInformacionProductoButton.addActionListener(this);
        eliminarLotesVencidosButton = new JButton("ELIMINAR LOTES VENCIDOS");
        eliminarLotesVencidosButton.addActionListener(this);
        agregarCategoriaButton = new JButton("AGREGAR CATEGORIA");
        agregarCategoriaButton.addActionListener(this);
        recibirCargaButton = new JButton("RECIBIR CARGA");
        recibirCargaButton.addActionListener(this);
        salirDeLaAplicacion = new JButton("SALIR DE LA APLICACIÓN");
        salirDeLaAplicacion.addActionListener(this);

        JPanel seccionArriba = new JPanel();
        seccionArriba.setLayout(new GridLayout(2,2,100,100));
        seccionArriba.add(verInformacionProductoButton);
        seccionArriba.add(eliminarLotesVencidosButton);
        seccionArriba.add(agregarCategoriaButton);
        seccionArriba.add(recibirCargaButton);

        JPanel seccionAbajo = new JPanel();
        seccionAbajo.setLayout(new BorderLayout());
        seccionAbajo.add(salirDeLaAplicacion, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(seccionArriba, BorderLayout.CENTER);
        add(seccionAbajo, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(verInformacionProductoButton)){
            inventarioInterfaz.verInformacionProducto();
        } else if (e.getSource().equals(eliminarLotesVencidosButton)) {
            inventarioInterfaz.eliminarLotesVencidos();
        } else if (e.getSource().equals(agregarCategoriaButton)){
            inventarioInterfaz.agregarCategoria();
        } else if (e.getSource().equals(recibirCargaButton)){
            inventarioInterfaz.recibirCarga();
        } else if (e.getSource().equals(salirDeLaAplicacion)){
            inventarioInterfaz.salirDeLaAplicacion();
        }
    }
}
