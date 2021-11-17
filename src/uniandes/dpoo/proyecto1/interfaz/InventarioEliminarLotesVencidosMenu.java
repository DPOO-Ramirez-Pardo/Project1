package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.procesamiento.Inventario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InventarioEliminarLotesVencidosMenu extends JPanel implements ActionListener {
    private Inventario inventario;
    private InventarioInterfaz inventarioInterfaz;
    private JTextField nombreProducto;
    private JTextField codigoProducto;
    private JButton eliminarLotesButton;
    private JButton cancelarButton;

    private KeyAdapter numberOnly = new KeyAdapter() {
        public void keyPressed(KeyEvent ke) {
            if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                codigoProducto.setEditable(true);
            } else {
                codigoProducto.setEditable(false);
            }
        }
    };

    public InventarioEliminarLotesVencidosMenu(Inventario inventario, InventarioInterfaz inventarioInterfaz){
        this.inventario = inventario;
        this.inventarioInterfaz = inventarioInterfaz;
        nombreProducto = new JTextField();
        codigoProducto = new JTextField();
        codigoProducto.addKeyListener(numberOnly);
        eliminarLotesButton = new JButton("ELIMINAR LOTES");
        eliminarLotesButton.addActionListener(this);
        cancelarButton = new JButton("CANCELAR");
        cancelarButton.addActionListener(this);

        setLayout(new BorderLayout());

        JPanel seccionArriba = new JPanel();
        seccionArriba.setLayout(new GridLayout(2,2,25,25));
        seccionArriba.add(new JLabel("Nombre Producto:"));
        seccionArriba.add(new JLabel("Codigo Producto: "));
        seccionArriba.add(nombreProducto);
        seccionArriba.add(codigoProducto);
        add(seccionArriba, BorderLayout.NORTH);

        JPanel seccionBotones = new JPanel();
        seccionBotones.setLayout(new GridLayout(1,2,25,0));
        seccionBotones.add(eliminarLotesButton);
        seccionBotones.add(cancelarButton);

        JPanel seccionAbajo = new JPanel();
        seccionAbajo.setLayout(new BorderLayout());
        seccionAbajo.add(seccionBotones, BorderLayout.EAST);
        add(seccionAbajo, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cancelarButton)) {
            inventarioInterfaz.volverMainMenu(2);
        } else if (e.getSource().equals(eliminarLotesButton)){
            eliminarLotesProducto();
        }
    }

    private void eliminarLotesProducto() {
        if (!nombreProducto.getText().isEmpty() && !codigoProducto.getText().isEmpty()){
            JOptionPane.showMessageDialog(inventarioInterfaz,
                    "Por favor llene únicamente el nombre ó el código, no ambos.",
                    "¡Confuso!", JOptionPane.PLAIN_MESSAGE);
        } else if (!nombreProducto.getText().isEmpty()){
            try {
                inventario.eliminarLotesVencidosPorNombre(nombreProducto.getText());
                inventarioInterfaz.volverMainMenu(2);
                JOptionPane.showMessageDialog(inventarioInterfaz, "Se eliminaron los lotes exitosamente.",
                        "¡Éxito!", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(inventarioInterfaz, exception.getMessage(),
                        "Exception!", JOptionPane.PLAIN_MESSAGE);
            }
        } else if (!codigoProducto.getText().isEmpty()){
            try {
                inventario.eliminarLotesVencidosPorCodigo(Integer.parseInt(codigoProducto.getText()));
                inventarioInterfaz.volverMainMenu(2);
                JOptionPane.showMessageDialog(inventarioInterfaz, "Se eliminaron los lotes exitosamente.",
                        "¡Éxito!", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(inventarioInterfaz, exception.getMessage(),
                        "Exception!", JOptionPane.PLAIN_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(inventarioInterfaz, "Por favor llene el nombre ó el código.",
                    "¡Vacío!", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
