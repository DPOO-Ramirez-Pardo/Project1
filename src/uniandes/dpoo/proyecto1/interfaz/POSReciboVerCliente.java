package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.Recibo;
import uniandes.dpoo.proyecto1.modelo.RecibosClienteMes;

import javax.swing.*;
import java.awt.*;

public class POSReciboVerCliente extends JPanel {
    private JList recibosLista;
    private JPanel finalRecibo;
    private RecibosClienteMes recibosClienteMes;

    public POSReciboVerCliente(RecibosClienteMes recibosClienteMes){
        this.recibosClienteMes = recibosClienteMes;

        finalRecibo = new JPanel();
        inicializarFinalRecibo();

        recibosLista = new JList();

        setLayout(new BorderLayout());
        add(recibosLista, BorderLayout.NORTH);
        add(finalRecibo, BorderLayout.SOUTH);
    }

    public void setRecibosClienteMes(RecibosClienteMes recibosClienteMes){
        this.recibosClienteMes = recibosClienteMes;
        finalRecibo.removeAll();
        inicializarFinalRecibo();
        this.revalidate();
        this.repaint();
    }

    private void inicializarFinalRecibo() {
        finalRecibo.setLayout(new GridLayout(3,1,0,0));
        finalRecibo.add(new JLabel("El Recibo no ha sido seleccionado"));
    }

    public void mostrarRecibo(int index) {
        Recibo recibo = recibosClienteMes.getRecibosMes().get(index);
        try {
            generarNuevaJListRecibo(recibo.generarRecibo());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.revalidate();
        this.repaint();
    }

    private void generarNuevaJListRecibo(String reciboString) {
        String lineasRecibo[] = reciboString.split("\n");
        int relleno = 5;
        int inicio = 1;
        if(lineasRecibo[1].contains("cliente")){
            relleno = 7;
            inicio = 3;
        }
        if (lineasRecibo.length > relleno){
            String lineasProductos[] = new String[lineasRecibo.length - relleno];
            for (int i = inicio; i < lineasRecibo.length - 4; i++){
                lineasProductos[i - inicio] = lineasRecibo[i];
            }
            recibosLista = new JList(lineasProductos);
        } else{
            remove(recibosLista);
            recibosLista = new JList();
        }
        add(recibosLista, BorderLayout.NORTH);
        finalRecibo.removeAll();
        finalRecibo.add(new JLabel(lineasRecibo[lineasRecibo.length - 3]));
        finalRecibo.add(new JLabel(lineasRecibo[lineasRecibo.length - 2]));
        finalRecibo.add(new JLabel(lineasRecibo[lineasRecibo.length - 1]));
    }
}
