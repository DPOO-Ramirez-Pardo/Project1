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
import java.util.ArrayList;

public class POSListaProductos extends JPanel implements ActionListener, ListSelectionListener {
    private POS pos;
    private JButton agregarProductoButton;
    private JList listaProductos;
    private JPanel finalRecibo;
    private POSPedidoMenu pedidoMenu;
    private Font listaFont;


    private static final int NO_SECTION = 0;
    private static final int CHOOSE_SECTION = 6;
    private static final int CABEZA_SECTION = 1;
    private static final int PRODUCTOS_SECTION = 2;
    private static final int PROMOCIONES_SECTION = 3;
    private static final int EXTRACTO_SECTION = 4;
    private static final int PUNTOS_SECTION = 5;

    private void estilizarLista(Object lineasProductos[]){
        remove(listaProductos);
        listaProductos = new JList(lineasProductos);
        listaProductos.setFixedCellWidth(200);
        listaProductos.setFixedCellHeight(25);
        listaProductos.setBackground(Color.WHITE);
        listaProductos.setForeground(Color.BLACK);
        listaProductos.setSelectionBackground(Color.ORANGE);
        listaProductos.setSelectionForeground(Color.WHITE);
        listaProductos.addListSelectionListener(this);
        add(listaProductos, BorderLayout.NORTH);
    }

    public void actualizarLista(){
        try {
            String reciboString = pos.generarRecibo();
            generarNuevaJListRecibo(reciboString);
            this.revalidate();
            this.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void generarNuevaJListRecibo(String reciboString) {
        String lineasRecibo[] = reciboString.split("\n");
        ArrayList<String> lineasProductos = new ArrayList<>();
        ArrayList<String> lineasFinal = new ArrayList<>();
        int currentState = NO_SECTION;
        for (int i = 0; i < lineasRecibo.length; i++) {
            if (lineasRecibo[i].equals("-----")) currentState = CHOOSE_SECTION;
            else if (currentState == CHOOSE_SECTION){
                if (lineasRecibo[i].equals("Cabeza")) currentState = CABEZA_SECTION;
                else if (lineasRecibo[i].equals("Productos")) currentState = PRODUCTOS_SECTION;
                else if (lineasRecibo[i].equals("Promociones")) currentState = PROMOCIONES_SECTION;
                else if (lineasRecibo[i].equals("Extracto")) currentState = EXTRACTO_SECTION;
                else if (lineasRecibo[i].equals("Puntos")) currentState = PUNTOS_SECTION;
                i++;
            } else if (currentState == CABEZA_SECTION){

            } else if (currentState == PRODUCTOS_SECTION){
                if (!lineasRecibo[i].isEmpty()) lineasProductos.add(lineasRecibo[i]);
            } else if (currentState == PROMOCIONES_SECTION ||
                    currentState == EXTRACTO_SECTION || currentState == PUNTOS_SECTION){
                if (!lineasRecibo[i].isEmpty()) lineasFinal.add(lineasRecibo[i]);
            }
        }
        Object[] array;
        if (lineasProductos.size() == 0) array = new Object[0];
        else array = lineasProductos.toArray();
        estilizarLista(array);
        finalRecibo.removeAll();
        finalRecibo.setLayout(new GridLayout(lineasFinal.size(),1,0,1));
        for (int i = 0; i < lineasFinal.size(); i++) {
            finalRecibo.add(new JLabel(lineasFinal.get(i)));
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
