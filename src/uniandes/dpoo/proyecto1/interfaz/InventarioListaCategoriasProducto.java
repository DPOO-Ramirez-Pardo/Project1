package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.Categoria;
import uniandes.dpoo.proyecto1.modelo.Producto;
import uniandes.dpoo.proyecto1.procesamiento.Inventario;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventarioListaCategoriasProducto extends JPanel implements ActionListener {
    private Inventario inventario;
    private InventarioInterfaz inventarioInterfaz;
    private Producto producto;
    private JButton agregarCategoriaButton;
    private JList listaCategorias;

    public InventarioListaCategoriasProducto(Inventario inventario, InventarioInterfaz inventarioInterfaz){
        this.inventario = inventario;
        this.inventarioInterfaz = inventarioInterfaz;

        setLayout(new BorderLayout());

        listaCategorias = new JList();
        add(listaCategorias, BorderLayout.NORTH);

        agregarCategoriaButton = new JButton("AGREGAR CATEGORIA");
        agregarCategoriaButton.addActionListener(this);
        add(agregarCategoriaButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(agregarCategoriaButton)){
            String nombreCategoria = (String) JOptionPane.showInputDialog(inventarioInterfaz,
                    "Introduzca la categoría a la cual quiere añadir el producto:","Agregar Categoria",
                    JOptionPane.PLAIN_MESSAGE, null, null, "");
            try {
                inventario.categorizarProductoPorCodigo(nombreCategoria, producto.getCodigo());
                actualizarLista();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(inventarioInterfaz, exception.getMessage(),
                        "Exception!", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }



    private void actualizarLista() {
        if( producto != null){
            ArrayList<Categoria> categorias = producto.getCategorias();
            if (categorias.size() > 0){
                String nombresCategorias [] = new String[categorias.size()];
                for (int i = 0; i < nombresCategorias.length; i++){
                    nombresCategorias[i] = categorias.get(i).getNombre();
                }
                remove(listaCategorias);
                listaCategorias = new JList(nombresCategorias);
                add(listaCategorias, BorderLayout.NORTH);
            } else {
                remove(listaCategorias);
                listaCategorias = new JList();
                add(listaCategorias, BorderLayout.NORTH);
            }
        } else {
            remove(listaCategorias);
            listaCategorias = new JList();
            add(listaCategorias, BorderLayout.NORTH);
        }
        this.revalidate();
        this.repaint();
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        actualizarLista();
    }
}
