package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.CantidadProducto;
import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class POSPedidoMenu extends JPanel implements ActionListener {
    private JButton registrarClienteButton;
    private JButton cancelarPedidoButton;
    private JButton generarReciboButton;
    private JButton anadirTitularButton;
    private JButton agregarProductoButton;

    private POSListaProductos listaProductos;
    private POSInformacionPedidoCantidadProducto informacionPedidoCantidadProducto;
    private POSMenuAgregarProducto menuAgregarProducto;

    private JPanel barraAbajo;

    private POS pos;
    private POSInterfaz parent;

    private boolean agregandoProducto;

    public POSPedidoMenu(POS pos, POSInterfaz parent){
        setLayout(new BorderLayout());

        this.pos = pos;
        this.parent = parent;
        registrarClienteButton = new JButton("REGISTRAR CLIENTE");
        cancelarPedidoButton = new JButton("CANCELAR PEDIDO");
        generarReciboButton = new JButton("GENERAR RECIBO");
        anadirTitularButton = new JButton("AÑADIR TITULAR");
        agregarProductoButton = new JButton("AGREGAR PRODUCTO");

        registrarClienteButton.addActionListener(this);
        cancelarPedidoButton.addActionListener(this);
        generarReciboButton.addActionListener(this);
        anadirTitularButton.addActionListener(this);
        agregarProductoButton.addActionListener(this);

        menuAgregarProducto = new POSMenuAgregarProducto(pos, this, parent);
        listaProductos = new POSListaProductos(pos, this, agregarProductoButton);

        barraAbajo = new JPanel();
        barraAbajo.setLayout(new GridLayout(1,4,30,0));
        barraAbajo.add(anadirTitularButton);
        barraAbajo.add(registrarClienteButton);
        barraAbajo.add(cancelarPedidoButton);
        barraAbajo.add(generarReciboButton);

        add(menuAgregarProducto, BorderLayout.CENTER);
        add(listaProductos, BorderLayout.EAST);
        add(barraAbajo, BorderLayout.SOUTH);

        agregandoProducto = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(anadirTitularButton)){
            añadirTitular();
        } else if (e.getSource().equals(registrarClienteButton)){
            parent.registrarCliente();
        } else if (e.getSource().equals(cancelarPedidoButton)){
            pos.cancelarRecibo();
            parent.volverMainMenu(0);
        } else if (e.getSource().equals(generarReciboButton)){
            try {
                pos.cerrarRecibo();
                parent.volverMainMenu(0);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (e.getSource().equals(agregarProductoButton)){
            agregarProducto();
        }
    }

    private void añadirTitular() {

    }

    public void verInformacionProducto(CantidadProducto cantidadProducto){
        if (agregandoProducto){
            remove(menuAgregarProducto);
        } else {
            remove(informacionPedidoCantidadProducto);
        }
        informacionPedidoCantidadProducto = new POSInformacionPedidoCantidadProducto(pos, parent, this, cantidadProducto);
        add(informacionPedidoCantidadProducto, BorderLayout.CENTER);
        listaProductos.hacerVisibleAgregarProductoButton();
        agregandoProducto = false;
        this.revalidate();
        this.repaint();
    }

    public void actualizarLista(){
        listaProductos.actualizarLista();
        this.revalidate();
        this.repaint();
    }

    private void agregarProducto() {
        remove(informacionPedidoCantidadProducto);
        add(menuAgregarProducto, BorderLayout.CENTER);
        agregandoProducto = true;
        this.revalidate();
        this.repaint();
    }

    public void eliminarProducto() {
        remove(informacionPedidoCantidadProducto);
        this.revalidate();
        this.repaint();
    }
}
