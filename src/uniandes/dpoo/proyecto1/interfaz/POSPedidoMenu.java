package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.exceptions.ClienteNoAñadidoException;
import uniandes.dpoo.proyecto1.exceptions.PuntosMayoresTotalException;
import uniandes.dpoo.proyecto1.exceptions.SinPuntosSuficientesException;
import uniandes.dpoo.proyecto1.exceptions.SinReciboActualException;
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
    private JButton redimirPuntosButton;

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
        redimirPuntosButton = new JButton("REDIMIR PUNTOS");

        registrarClienteButton.addActionListener(this);
        cancelarPedidoButton.addActionListener(this);
        generarReciboButton.addActionListener(this);
        anadirTitularButton.addActionListener(this);
        agregarProductoButton.addActionListener(this);
        redimirPuntosButton.addActionListener(this);

        menuAgregarProducto = new POSMenuAgregarProducto(pos, this, parent);
        listaProductos = new POSListaProductos(pos, this, agregarProductoButton);

        barraAbajo = new JPanel();
        barraAbajo.setLayout(new GridLayout(1,5,30,0));
        barraAbajo.add(anadirTitularButton);
        barraAbajo.add(redimirPuntosButton);
        barraAbajo.add(registrarClienteButton);
        barraAbajo.add(cancelarPedidoButton);
        barraAbajo.add(generarReciboButton);

        add(menuAgregarProducto, BorderLayout.CENTER);
        add(listaProductos, BorderLayout.EAST);
        add(barraAbajo, BorderLayout.SOUTH);

        agregandoProducto = true;
    }

    public void reiniciar(){
        listaProductos.reiniciar();
        this.agregarProducto();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(anadirTitularButton)){
            añadirTitular();
        } else if (e.getSource().equals(registrarClienteButton)){
            parent.registrarCliente(1);
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
        } else if (e.getSource().equals(redimirPuntosButton)){
            redimirPuntos();
        }
    }

    private void redimirPuntos(){
        try {
            if (!pos.reciboTieneCliente()) {
                JOptionPane.showMessageDialog(parent, "No hay Titular del Recibo", "¡Error!",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                Object[] options = {"Redimir mayor cantidad de puntos posibles", "Redimir una cantidad específica de puntos",
                        "No redimir puntos", "Cancelar"};
                int opcion = JOptionPane.showOptionDialog(parent, null, "Redimir Puntos",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[3]);
                if (opcion == 0){
                    pos.redimirMaximoPuntos();
                    listaProductos.actualizarLista();
                    this.revalidate();
                    this.repaint();
                    JOptionPane.showMessageDialog(parent,
                            "¡Se redimirán " + pos.getPuntosRedimidos() + " puntos!",
                            "¡Éxito!", JOptionPane.PLAIN_MESSAGE);
                } else if (opcion == 1){
                    redimirCantidadArbitrariaPuntos();
                } else if (opcion == 2){
                    try {
                        pos.redimirPuntos(0);
                        listaProductos.actualizarLista();
                        this.revalidate();
                        this.repaint();
                        JOptionPane.showMessageDialog(parent, "¡Se actualizó a cero!",
                                "¡Éxito!", JOptionPane.PLAIN_MESSAGE);
                    } catch (ClienteNoAñadidoException | SinPuntosSuficientesException | PuntosMayoresTotalException e) {}
                }
            }
        } catch (SinReciboActualException e) {
            e.printStackTrace();
        }

    }

    private void redimirCantidadArbitrariaPuntos() throws SinReciboActualException {
        int puntos = Integer.parseInt(JOptionPane.showInputDialog(parent, "Introduzca la cantidad de puntos a redimir: ",
                "Redimir Puntos", JOptionPane.PLAIN_MESSAGE));
        try {
            pos.redimirPuntos(puntos);
            listaProductos.actualizarLista();
            this.revalidate();
            this.repaint();
            JOptionPane.showMessageDialog(parent, "¡Se redimirán esos puntos!",
                    "¡Éxito!", JOptionPane.PLAIN_MESSAGE);
        } catch (SinPuntosSuficientesException | PuntosMayoresTotalException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage(), "¡Error!", JOptionPane.PLAIN_MESSAGE);
        } catch (ClienteNoAñadidoException e) {
            e.printStackTrace();
        }
    }

    private void añadirTitular() {
        String textoCedula = (String) JOptionPane.showInputDialog(parent,
                "Introduzca la cédula del cliente:","Añadir Titular",
                JOptionPane.PLAIN_MESSAGE, null, null, "");
        if (textoCedula != null){
            try{
                int cedula = Integer.parseInt(textoCedula);
                if (pos.existeCliente(cedula)){
                    pos.añadirTitular(cedula);
                    listaProductos.actualizarLista();
                    this.revalidate();
                    this.repaint();
                    JOptionPane.showMessageDialog(parent, "¡El Cliente fue añadido exitosamente!",
                            "¡Éxito!", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parent, "¡El Cliente no Existe!",
                            "¡Entrada Inválida!", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "¡Introduzca un número por favor!",
                        "¡Entrada Inválida!", JOptionPane.PLAIN_MESSAGE);
            } catch (SinReciboActualException e) {
                e.printStackTrace();
            }
        }
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
        if(!agregandoProducto){
            remove(informacionPedidoCantidadProducto);
            add(menuAgregarProducto, BorderLayout.CENTER);
            agregandoProducto = true;
            this.revalidate();
            this.repaint();
        }
    }

    public void eliminarProducto() {
        remove(informacionPedidoCantidadProducto);
        this.revalidate();
        this.repaint();
    }
}
