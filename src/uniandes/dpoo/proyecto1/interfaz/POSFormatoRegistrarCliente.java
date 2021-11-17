package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.EstadoCivil;
import uniandes.dpoo.proyecto1.modelo.Sexo;
import uniandes.dpoo.proyecto1.modelo.SituacionEmpleo;
import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class POSFormatoRegistrarCliente extends JPanel implements ActionListener {
    private int back;
    private POS pos;
    private POSInterfaz posInterfaz;

    private JTextField nombreCliente;
    private JTextField cedulaCliente;
    private JTextField edadCliente;
    private JComboBox<String> generoCliente;
    private JComboBox<String> situacionEmpleoCliente;
    private JComboBox<String> estadoCivilCliente;

    private JButton registrarButton;
    private JButton cancelarButton;

    private KeyAdapter numberOnly = new KeyAdapter() {
        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                cedulaCliente.setEditable(true);
                edadCliente.setEditable(true);
            } else {
                cedulaCliente.setEditable(false);
                edadCliente.setEditable(false);
            }
        }
    };


    public POSFormatoRegistrarCliente(POS pos, POSInterfaz posInterfaz, int back){
        this.pos = pos;
        this.posInterfaz = posInterfaz;
        this.back = back;

        nombreCliente = new JTextField();

        cedulaCliente = new JTextField();
        cedulaCliente.addKeyListener(numberOnly);

        edadCliente = new JTextField();
        edadCliente.addKeyListener(numberOnly);

        generoCliente = new JComboBox<>();
        generoCliente.addItem("Masculino");
        generoCliente.addItem("Femenino");
        generoCliente.addItem("Indefinido");

        situacionEmpleoCliente = new JComboBox<>();
        situacionEmpleoCliente.addItem("Empleado");
        situacionEmpleoCliente.addItem("Independiente");
        situacionEmpleoCliente.addItem("Estudiante");
        situacionEmpleoCliente.addItem("Desempleado");

        estadoCivilCliente = new JComboBox<>();
        estadoCivilCliente.addItem("Soltero");
        estadoCivilCliente.addItem("Casado");
        estadoCivilCliente.addItem("Otro");

        registrarButton = new JButton("REGISTRAR");
        registrarButton.setBackground(Color.green);
        registrarButton.setForeground(Color.WHITE);
        registrarButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        registrarButton.addActionListener(this);
        cancelarButton = new JButton("CANCELAR");
        cancelarButton.setBackground(Color.red);
        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
        cancelarButton.addActionListener(this);

        setLayout(new GridLayout(1,2,100,0));


        JPanel leftLayout = new JPanel();
        leftLayout.setLayout(new GridLayout(12, 1, 0, 0));
        leftLayout.add(new JLabel("Nombre:"));
        leftLayout.add(nombreCliente);
        leftLayout.add(new JLabel("Cedula:"));
        leftLayout.add(cedulaCliente);
        leftLayout.add(new JLabel("Edad"));
        leftLayout.add(edadCliente);
        leftLayout.add(new JLabel("Sexo:"));
        leftLayout.add(generoCliente);
        leftLayout.add(new JLabel("Situación Empleo:"));
        leftLayout.add(situacionEmpleoCliente);
        leftLayout.add(new JLabel("Estado Civil:"));
        leftLayout.add(estadoCivilCliente);

        JPanel rightLayout = new JPanel();
        rightLayout.setLayout(new BorderLayout());
        JPanel rightBottomLayout = new JPanel();
        rightBottomLayout.setLayout(new GridLayout(2, 1, 0,50));
        rightBottomLayout.add(registrarButton);
        rightBottomLayout.add(cancelarButton);

        rightLayout.add(rightBottomLayout, BorderLayout.SOUTH);

        add(leftLayout);
        add(rightLayout);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(registrarButton)){
            registrarCliente();
        } else if (e.getSource().equals(cancelarButton)){
            volverMenu();
        }
    }

    private void registrarCliente() {
        try {
            pos.registrarClienteEnSistemaPuntos(nombreCliente.getText(),
                    Integer.parseInt(cedulaCliente.getText()),
                    Integer.parseInt(edadCliente.getText()), 0,
                    Sexo.valueOf((String)generoCliente.getSelectedItem()),
                    SituacionEmpleo.valueOf((String)situacionEmpleoCliente.getSelectedItem()),
                    EstadoCivil.valueOf((String)estadoCivilCliente.getSelectedItem()));
            volverMenu();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(posInterfaz, exception.getMessage(),
                    "Exception!", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void volverMenu() {
        if (back == 0) posInterfaz.volverMainMenu(1);
        else if (back == 1) posInterfaz.reanudarPedido();
    }

    public void setBack(int back) {
        this.back = back;
    }
}
