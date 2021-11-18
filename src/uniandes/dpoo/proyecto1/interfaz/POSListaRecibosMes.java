package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.Recibo;
import uniandes.dpoo.proyecto1.modelo.RecibosClienteMes;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class POSListaRecibosMes extends JPanel implements ListSelectionListener {
    private POSInterfaz posInterfaz;
    private POSVerClienteMenu verClienteMenu;
    private RecibosClienteMes recibosClienteMes;
    private JList recibosLista;

    public POSListaRecibosMes(POSInterfaz posInterfaz, POSVerClienteMenu verClienteMenu,
                              RecibosClienteMes recibosClienteMes){
        this.posInterfaz = posInterfaz;
        this.verClienteMenu = verClienteMenu;
        this.recibosClienteMes = recibosClienteMes;
        setLayout(new BorderLayout());
        inicializarLista();
    }

    private void inicializarLista(){
        if (recibosClienteMes != null){
            ArrayList<Recibo> recibos = recibosClienteMes.getRecibosMes();
            String recibosString [] = new String[recibos.size()];
            for (int i = 0; i < recibosString.length; i++){
                recibosString[i] = recibos.get(i).getFecha().toString();
            }
            recibosLista = new JList(recibosString);
            recibosLista.addListSelectionListener(this);
        } else {
            recibosLista = new JList();
        }
        add(recibosLista, BorderLayout.CENTER);
    }

    public void setRecibosClienteMes(RecibosClienteMes recibosClienteMes){
        this.recibosClienteMes = recibosClienteMes;
        remove(recibosLista);
        inicializarLista();
        this.revalidate();
        this.repaint();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(recibosLista.getSelectedIndex() != -1){
            verClienteMenu.mostrarRecibo(recibosLista.getSelectedIndex());
        }
    }
}
