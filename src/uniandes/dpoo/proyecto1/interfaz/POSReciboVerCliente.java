package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.Recibo;
import uniandes.dpoo.proyecto1.modelo.RecibosClienteMes;

import javax.swing.*;
import java.awt.*;

public class POSReciboVerCliente extends JPanel {
    private JList recibosLista;
    private RecibosClienteMes recibosClienteMes;

    public POSReciboVerCliente(RecibosClienteMes recibosClienteMes){
        this.recibosClienteMes = recibosClienteMes;

        recibosLista = new JList();

        setLayout(new BorderLayout());
        add(recibosLista, BorderLayout.NORTH);
    }

    public void setRecibosClienteMes(RecibosClienteMes recibosClienteMes){
        this.recibosClienteMes = recibosClienteMes;
        this.revalidate();
        this.repaint();
    }

    public void mostrarRecibo(int index) {
        Recibo recibo = recibosClienteMes.getRecibosMes().get(index);
        try {
            remove(recibosLista);
            generarNuevaJListRecibo(recibo.generarRecibo());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.revalidate();
        this.repaint();
    }

    private void generarNuevaJListRecibo(String reciboString) {
        String lineasRecibo[] = reciboString.split("\n");
        recibosLista = new JList(lineasRecibo);
        add(recibosLista, BorderLayout.NORTH);
    }
}
