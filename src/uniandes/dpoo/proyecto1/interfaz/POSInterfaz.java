package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;

public class POSInterfaz extends JFrame {
    private POS pos;
    private POSMainMenu mainMenu;
    private POSPedidoMenu pedidoMenu;
    private POSFormatoRegistrarCliente formatoRegistrarCliente;

    public POSInterfaz(){
        setTitle("Lights Out");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);
        setLayout(new BorderLayout());
        setBackground(Color.white);

        pos = new POS();
        mainMenu = new POSMainMenu(pos, this);
        pedidoMenu = new POSPedidoMenu(pos, this);
        formatoRegistrarCliente = new POSFormatoRegistrarCliente(pos, this, 0);
        add(mainMenu, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String args[]){
        new POSInterfaz();
    }

    public void iniciarPedido(){
        try {
            pos.iniciarPedido();
            remove(mainMenu);
            add(pedidoMenu, BorderLayout.CENTER);
            setVisible(false);
            this.validate();
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrarCliente() {
        remove(mainMenu);
        add(formatoRegistrarCliente, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void verHistorialCliente() {
    }

    public void salirDeLaAplicacion() {
    }

    public void volverMainMenu(int back) {
        if (back == 0) remove(pedidoMenu);
        else if (back == 1) remove(formatoRegistrarCliente);
        add(mainMenu, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }


}
