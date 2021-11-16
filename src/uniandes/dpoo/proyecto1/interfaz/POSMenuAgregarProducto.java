package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class POSMenuAgregarProducto extends JPanel implements ActionListener {
    private POS pos;
    private POSPedidoMenu pedidoMenu;
    private POSInterfaz posInterfaz;

    private JTextField nombreProducto;
    private JTextField codigoProducto;
    private JTextField cantidad;
    private JButton agregarProductoButton;

    private KeyAdapter numberOnly = new KeyAdapter() {
        public void keyPressed(KeyEvent ke) {
            if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                codigoProducto.setEditable(true);
                cantidad.setEditable(true);
            } else {
                codigoProducto.setEditable(false);
                cantidad.setEditable(false);
            }
        }
    };

    public POSMenuAgregarProducto(POS pos, POSPedidoMenu pedidoMenu, POSInterfaz posInterfaz) {
        this.pos = pos;
        this.pedidoMenu = pedidoMenu;
        this.posInterfaz = posInterfaz;
        setLayout(new GridLayout(4,2,50,25));

        nombreProducto = new JTextField();
        codigoProducto = new JTextField();
        codigoProducto.addKeyListener(numberOnly);
        cantidad = new JTextField();
        cantidad.addKeyListener(numberOnly);
        agregarProductoButton = new JButton("Agregar Producto");
        agregarProductoButton.addActionListener(this);

        add(new JLabel("Nombre:"));
        add(nombreProducto);
        add(new JLabel("Codigo:"));
        add(codigoProducto);
        add(new JLabel("Cantidad:"));
        add(cantidad);
        add(agregarProductoButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(agregarProductoButton)){
            if (!nombreProducto.getText().isEmpty() && !codigoProducto.getText().isEmpty()){
                JOptionPane.showMessageDialog(posInterfaz,
                        "Por favor llene únicamente el nombre ó el código, no ambos.",
                        "¡Confuso!", JOptionPane.PLAIN_MESSAGE);
            } else if (!nombreProducto.getText().isEmpty()){
                try {
                    pos.agregarProductoPorNombre(nombreProducto.getText());
                    pos.agregarCantidadProducto(Float.parseFloat(cantidad.getText()));
                    pedidoMenu.actualizarLista();
                } catch (Exception exception) {
                    pos.cancelarProducto();
                    JOptionPane.showMessageDialog(posInterfaz, exception.getMessage(),
                            "Exception!", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (!codigoProducto.getText().isEmpty()){
                try {
                    pos.agregarProductoPorCodigo(Integer.parseInt(codigoProducto.getText()));
                    pos.agregarCantidadProducto(Float.parseFloat(cantidad.getText()));
                    pedidoMenu.actualizarLista();
                } catch (Exception exception) {
                    pos.cancelarProducto();
                    exception.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(posInterfaz, "Por favor llene el nombre ó el código.",
                        "¡Vacío!", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
