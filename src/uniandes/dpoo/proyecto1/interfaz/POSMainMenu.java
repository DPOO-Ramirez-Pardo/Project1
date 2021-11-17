package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class POSMainMenu extends JPanel implements ActionListener {
    private POS pos;
    private JButton iniciarPedidoButton;
    private JButton registrarClienteButton;
    private JButton verHistorialClienteButton;
    private JButton salirDeLaAplicacion;
    private JPanel barraAbajo;
    private JPanel barraDerecha;
    private JPanel barraDerechaAbajo;

    private POSInterfaz posInterfaz;

    public POSMainMenu(POS pos, POSInterfaz posInterfaz){
        this.pos = pos;
        this.posInterfaz = posInterfaz;
        setLayout(new BorderLayout());

        iniciarPedidoButton = new JButton("INICIAR PEDIDO");
        registrarClienteButton = new JButton("REGISTRAR CLIENTE");
        verHistorialClienteButton = new JButton("VER HISTORIAL CLIENTE");
        salirDeLaAplicacion = new JButton("SALIR DE LA APLICACIÓN");
        barraAbajo = new JPanel();
        barraDerecha = new JPanel();

        iniciarPedidoButton.setBackground(Color.green);
        iniciarPedidoButton.setForeground(Color.WHITE);
        iniciarPedidoButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        registrarClienteButton.setBackground(Color.blue);
        registrarClienteButton.setForeground(Color.WHITE);
        registrarClienteButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        verHistorialClienteButton.setBackground(Color.blue);
        verHistorialClienteButton.setForeground(Color.WHITE);
        verHistorialClienteButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        salirDeLaAplicacion.setBackground(Color.red);
        salirDeLaAplicacion.setForeground(Color.WHITE);
        salirDeLaAplicacion.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        iniciarPedidoButton.addActionListener(this);
        registrarClienteButton.addActionListener(this);
        verHistorialClienteButton.addActionListener(this);
        salirDeLaAplicacion.addActionListener(this);

        barraDerecha.setLayout(new BorderLayout());
        barraDerechaAbajo = new JPanel();
        barraDerechaAbajo.setLayout(new GridLayout(2, 1, 0, 24));
        barraDerechaAbajo.add(registrarClienteButton);
        barraDerechaAbajo.add(verHistorialClienteButton);
        barraDerecha.add(barraDerechaAbajo, BorderLayout.SOUTH);

        barraAbajo.add(salirDeLaAplicacion, BorderLayout.EAST);

        add(iniciarPedidoButton, BorderLayout.CENTER);
        add(barraDerecha, BorderLayout.EAST);
        add(barraAbajo, BorderLayout.SOUTH);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(iniciarPedidoButton)){
            posInterfaz.iniciarPedido();
        } else if (e.getSource().equals(registrarClienteButton)) {
            posInterfaz.registrarCliente();
        } else if (e.getSource().equals(verHistorialClienteButton)){
            posInterfaz.verHistorialCliente();
        } else if (e.getSource().equals(salirDeLaAplicacion)){
            posInterfaz.salirDeLaAplicacion();
        }
    }
}
