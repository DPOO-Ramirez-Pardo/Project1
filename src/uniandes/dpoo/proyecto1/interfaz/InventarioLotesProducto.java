package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.Lote;
import uniandes.dpoo.proyecto1.modelo.Producto;
import uniandes.dpoo.proyecto1.modelo.ProductoEmpaquetado;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class InventarioLotesProducto extends JFrame implements ListSelectionListener {
    private ArrayList<Lote> lotes;
    private JList fechasLotes;
    private JPanel informacionLote;
    private String unidad;
    public InventarioLotesProducto(Producto producto){
        String nombre = producto.getNombre();
        setTitle("Lotes "+nombre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600,400);
        setLayout(new BorderLayout());
        unidad = producto.getUnidad();
        lotes = producto.getLotes();
        if (lotes.size() > 0){
            String fechas [] = new String[lotes.size()];
            for (int i = 0; i < fechas.length; i++){
                fechas[i] = lotes.get(i).getFechaLlegada().toString();
            }
            fechasLotes = new JList(fechas);
            fechasLotes.addListSelectionListener(this);
            informacionLote = new JPanel();
            add(fechasLotes, BorderLayout.WEST);
            add(informacionLote, BorderLayout.EAST);
        } else{
            fechasLotes = null;
            add(new JLabel("No hay Lotes Disponibles!"), BorderLayout.CENTER);
        }
        setVisible(true);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        informacionLote.removeAll();
        if (fechasLotes.getSelectedIndex() != -1){
            Lote lote = lotes.get(fechasLotes.getSelectedIndex());
            String lineas[] = lote.stringInformacion(unidad).split("\n");
            informacionLote.setLayout(new GridLayout(lineas.length,1,0,25));
            for(String linea: lineas){
                informacionLote.add(new JLabel(linea));
            }
        }
        this.revalidate();
        this.repaint();
    }
}
