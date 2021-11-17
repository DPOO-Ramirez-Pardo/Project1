package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.CantidadProducto;
import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class POSListaProductos extends JPanel implements ActionListener, ListSelectionListener {
    private POS pos;
    private JButton agregarProductoButton;
    private JList listaProductos;
    private JPanel finalRecibo;
    private POSPedidoMenu pedidoMenu;
    private Font listaFont;

    private void estilizarLista(String lineasProductos[]){
        remove(listaProductos);
        listaProductos = new JList(lineasProductos);
        listaProductos.setFixedCellWidth(200);
        listaProductos.setFixedCellHeight(25);
        listaProductos.setBackground(Color.WHITE);
        listaProductos.setForeground(Color.BLACK);
        listaProductos.setSelectionBackground(Color.ORANGE);
        listaProductos.setSelectionForeground(Color.WHITE);
        listaProductos.addListSelectionListener(this);
        add(listaProductos);
    }

    public void actualizarLista(){
        try {
            String reciboString = pos.generarRecibo();
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
                estilizarLista(lineasProductos);
            } else{
                remove(listaProductos);
                listaProductos = new JList();
            }
            add(listaProductos, BorderLayout.NORTH);
            finalRecibo.removeAll();
            finalRecibo.add(new JLabel(lineasRecibo[lineasRecibo.length - 3]));
            finalRecibo.add(new JLabel(lineasRecibo[lineasRecibo.length - 2]));
            finalRecibo.add(new JLabel(lineasRecibo[lineasRecibo.length - 1]));
            this.revalidate();
            this.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public POSListaProductos(POS pos, POSPedidoMenu pedidoMenu, JButton agregarProductoButton){
        this.pos = pos;
        this.agregarProductoButton = agregarProductoButton;
        this.pedidoMenu = pedidoMenu;
        this.listaFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

        agregarProductoButton.addActionListener(this);

        listaProductos = new JList();
        setLayout(new BorderLayout());

        finalRecibo = new JPanel();
        finalRecibo.setLayout(new GridLayout(3, 1, 0, 1));
        JLabel subtotal = new JLabel("subtotal: 0");
        subtotal.setFont(listaFont);
        finalRecibo.add(subtotal);
        JLabel IVA = new JLabel("IVA: 0");
        IVA.setFont(listaFont);
        finalRecibo.add(IVA);
        JLabel total = new JLabel("total: 0");
        total.setFont(listaFont);
        finalRecibo.add(total);

        add(listaProductos, BorderLayout.NORTH);
        add(finalRecibo, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(200, 0));

    }

    public void hacerVisibleAgregarProductoButton(){
        add(agregarProductoButton, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(agregarProductoButton)){
            remove(agregarProductoButton);
            this.revalidate();
            this.repaint();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = listaProductos.getSelectedIndex();
        if (selectedIndex != -1){
            CantidadProducto cantidadProducto = pos.getCantidadProducto(selectedIndex);
            pedidoMenu.verInformacionProducto(cantidadProducto);
        }
    }

    public void reiniciar() {
        actualizarLista();
        try{
            remove(agregarProductoButton);
        } catch (Exception e){}
    }
}