package uniandes.dpoo.proyecto1.interfaz;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class POSVerClienteMenu extends JPanel implements ActionListener {
    private JComboBox<String> añoSeleccion;
    private JComboBox<String> mesSeleccion;
    private POSPanelMes panelMes;
    private POSListaRecibosMes listaRecibosMes;
    private POSReciboVerCliente reciboVerCliente;
    private JButton volver;

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
