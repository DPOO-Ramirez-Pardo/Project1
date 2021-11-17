package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.Producto;

import javax.swing.*;
import java.awt.*;

public class InventarioInformacionFinancieraProducto extends JFrame {
    public InventarioInformacionFinancieraProducto(String nombre, String informacionFinanciera){
        setTitle("Información Financiera "+nombre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600,400);
        setLayout(new GridLayout(3,1,0,24));
        String lines[] = informacionFinanciera.split("\n");
        for(String line: lines){
            JLabel jLabel = new JLabel(line);
            jLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
            add(jLabel);
        }
        setVisible(true);
    }
}
