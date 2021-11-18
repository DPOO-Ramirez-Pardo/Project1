package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.RecibosClienteMes;
import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class POSVerClienteMenu extends JPanel implements ActionListener, ItemListener {
    private JComboBox<String> añoSeleccion;
    private JComboBox<String> mesSeleccion;
    private POS pos;
    private int cedula;
    private POSInterfaz posInterfaz;
    private POSPanelMes panelMes;
    private POSListaRecibosMes listaRecibosMes;
    private POSReciboVerCliente reciboVerCliente;
    private JButton volver;

    public POSVerClienteMenu(POSInterfaz posInterfaz, POS pos, int cedula, int añoInicio, int añoActual){
        this.pos = pos;
        this.cedula = cedula;
        this.posInterfaz = posInterfaz;
        añoSeleccion = new JComboBox<>();
        for(int i = añoInicio; i <= añoActual; i++){
            añoSeleccion.addItem(Integer.toString(i));
        }
        añoSeleccion.addItemListener(this);
        mesSeleccion = new JComboBox<>();
        for(int i = 1; i < 13; i++){
            mesSeleccion.addItem(Integer.toString(i));
        }
        mesSeleccion.addItemListener(this);
        volver = new JButton("VOLVER");
        volver.addActionListener(this);
        panelMes = new POSPanelMes(null);
        listaRecibosMes = new POSListaRecibosMes(posInterfaz,this, null);
        reciboVerCliente = new POSReciboVerCliente(null);

        JPanel seccionSelectores = new JPanel();
        setLayout(new GridLayout(2,2,0,20));
        seccionSelectores.add(new JLabel("Año:"));
        seccionSelectores.add(añoSeleccion);
        seccionSelectores.add(new JLabel("Mes:"));
        seccionSelectores.add(mesSeleccion);

        JPanel seccionIzquierda = new JPanel();
        seccionIzquierda.setLayout(new BorderLayout());
        seccionIzquierda.add(seccionSelectores, BorderLayout.NORTH);
        seccionIzquierda.add(panelMes, BorderLayout.SOUTH);

        JPanel seccionCentral = new JPanel();
        seccionCentral.setLayout(new BorderLayout());
        seccionCentral.add(listaRecibosMes, BorderLayout.NORTH);

        JPanel seccionDerecha = new JPanel();
        seccionDerecha.setLayout(new BorderLayout());
        seccionDerecha.add(reciboVerCliente, BorderLayout.NORTH);
        seccionDerecha.add(volver, BorderLayout.SOUTH);

        setLayout(new GridLayout(1,3,25,0));
        add(seccionIzquierda);
        add(seccionCentral);
        add(seccionDerecha);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(volver)){
            posInterfaz.volverMainMenu(2);
        }
    }

    public void mostrarRecibo(int index) {
        reciboVerCliente.mostrarRecibo(index);
    }

    public void setCedula(int cedula){
        this.cedula = cedula;
        actualizarMenu();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        actualizarMenu();
    }

    private void actualizarMenu() {
        int mes = Integer.parseInt((String)mesSeleccion.getSelectedItem());
        int año = Integer.parseInt((String)añoSeleccion.getSelectedItem());
        RecibosClienteMes recibosClienteMes = pos.getRecibosClienteMes(cedula, mes, año);
        panelMes.setRecibosClienteMes(recibosClienteMes);
        listaRecibosMes.setRecibosClienteMes(recibosClienteMes);
        reciboVerCliente.setRecibosClienteMes(recibosClienteMes);
    }
}
