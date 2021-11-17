package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.Producto;
import uniandes.dpoo.proyecto1.procesamiento.Inventario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InventarioInformacionProductoMenu extends JPanel implements ActionListener {
    private Inventario inventario;
    private InventarioInterfaz inventarioInterfaz;
    private Producto producto;
    private JTextField nombreProducto;
    private JTextField codigoProducto;
    private JTextField descripcionProducto;
    private JTextField condicionAlmacenamientoProducto;
    private JTextField cantidadTotalDisponible;
    private ImagenProducto imagenProducto;
    private InventarioListaCategoriasProducto listaCategoriasProducto;

    private JButton buscarProductoNombre;
    private JButton buscarProductoCodigo;
    private JButton cambiarImagenButton;
    private JButton mostrarInformacionFinanciera;
    private JButton mostrarLotes;
    private JButton volver;

    private KeyAdapter numberOnly = new KeyAdapter() {
        public void keyPressed(KeyEvent ke) {
            if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                codigoProducto.setEditable(true);
            } else {
                codigoProducto.setEditable(false);
            }
        }
    };

    public InventarioInformacionProductoMenu(Inventario inventario, InventarioInterfaz inventarioInterfaz){
        this.inventario = inventario;
        this.inventarioInterfaz = inventarioInterfaz;
        this.producto = null;
        setLayout(new BorderLayout());

        nombreProducto = new JTextField();
        codigoProducto = new JTextField();
        codigoProducto.addKeyListener(numberOnly);
        descripcionProducto = new JTextField();
        descripcionProducto.setEditable(false);
        condicionAlmacenamientoProducto = new JTextField();
        condicionAlmacenamientoProducto.setEditable(false);
        cantidadTotalDisponible = new JTextField();
        cantidadTotalDisponible.setEditable(false);
        imagenProducto = new ImagenProducto();
        listaCategoriasProducto = new InventarioListaCategoriasProducto(inventario,inventarioInterfaz);
        buscarProductoCodigo = new JButton("BUSCAR POR CODIGO");
        buscarProductoCodigo.setBackground(Color.BLUE);
        buscarProductoCodigo.setForeground(Color.WHITE);
        buscarProductoCodigo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        buscarProductoCodigo.addActionListener(this);
        buscarProductoNombre = new JButton("BUSCAR POR NOMBRE");
        buscarProductoNombre.addActionListener(this);
        cambiarImagenButton = new JButton("CAMBIAR IMAGEN");
        cambiarImagenButton.addActionListener(this);
        mostrarInformacionFinanciera = new JButton("MOSTRAR INFORMACIÓN FINANCIERA");
        mostrarInformacionFinanciera.addActionListener(this);
        mostrarLotes = new JButton("MOSTRAR LOTES");
        mostrarLotes.addActionListener(this);
        volver = new JButton("VOLVER");
        volver.addActionListener(this);

        JPanel seccionArribaSuperior = new JPanel();
        seccionArribaSuperior.setLayout(new GridLayout(1,4,50,0));
        seccionArribaSuperior.add(new JLabel("Nombre"));
        seccionArribaSuperior.add(nombreProducto);
        seccionArribaSuperior.add(new JLabel("Codigo:"));
        seccionArribaSuperior.add(codigoProducto);

        JPanel seccionArribaIntermedia = new JPanel();
        seccionArribaIntermedia.setLayout(new GridLayout(1,2,100,0));
        seccionArribaIntermedia.add(buscarProductoNombre);
        seccionArribaIntermedia.add(buscarProductoCodigo);

        JPanel seccionArribaInferior = new JPanel();
        seccionArribaInferior.setLayout(new GridLayout(1,1,0,0));
        seccionArribaInferior.add(descripcionProducto);

        JPanel seccionArriba = new JPanel();
        seccionArriba.setLayout(new GridLayout(3,1,0,20));
        seccionArriba.add(seccionArribaSuperior);
        seccionArriba.add(seccionArribaIntermedia);
        seccionArriba.add(seccionArribaInferior);
        add(seccionArriba, BorderLayout.NORTH);

        JPanel seccionCentralIzquierda = new JPanel();
        seccionCentralIzquierda.setLayout(new GridLayout(4,1,0,10));
        seccionCentralIzquierda.add(new JLabel("Condición Almacenamiento:"));
        seccionCentralIzquierda.add(condicionAlmacenamientoProducto);
        seccionCentralIzquierda.add(new JLabel("Cantidad Total Disponible:"));
        seccionCentralIzquierda.add(cantidadTotalDisponible);

        JPanel seccionCentralMitad = new JPanel();
        seccionCentralMitad.setLayout(new BorderLayout());
        seccionCentralMitad.add(new JLabel("Imagen:"), BorderLayout.NORTH);
        seccionCentralMitad.add(imagenProducto, BorderLayout.CENTER);
        seccionCentralMitad.add(cambiarImagenButton, BorderLayout.SOUTH);

        JPanel seccionCentral = new JPanel();
        seccionCentral.setLayout(new GridLayout(1,3,50,0));
        seccionCentral.add(seccionCentralIzquierda);
        seccionCentral.add(seccionCentralMitad);
        seccionCentral.add(listaCategoriasProducto);
        add(seccionCentral, BorderLayout.CENTER);

        JPanel seccionAbajo = new JPanel();
        seccionAbajo.setLayout(new GridLayout(1,3,100,0));
        seccionAbajo.add(mostrarInformacionFinanciera);
        seccionAbajo.add(mostrarLotes);
        seccionAbajo.add(volver);
        add(seccionAbajo, BorderLayout.SOUTH);
    }

    public void setProducto(Producto producto){
        this.producto = producto;
        actualizarInformacion();
    }

    private void actualizarInformacion() {
        nombreProducto.setText(producto.getNombre());
        codigoProducto.setText(Integer.toString(producto.getCodigo()));
        descripcionProducto.setText(producto.getDescripcion());
        condicionAlmacenamientoProducto.setText(producto.mostrarCondicionAlmacenamiento());
        cantidadTotalDisponible.setText(Float.toString(producto.getCantidadTotal()));
        imagenProducto.setPath(producto.getPathImagen());
        listaCategoriasProducto.setProducto(producto);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buscarProductoNombre)){
            buscarProductoPorNombre();
        } else if (e.getSource().equals(buscarProductoCodigo)){
            buscarProductoPorCodigo();
        } else if (e.getSource().equals(cambiarImagenButton)){
            cambiarImagenProducto();
        } else if (e.getSource().equals(mostrarInformacionFinanciera)){
            mostrarInformacionFinancieraProducto();
        } else if (e.getSource().equals(mostrarLotes)){
            if(producto != null){
                new InventarioLotesProducto(producto);
            } else {
                JOptionPane.showMessageDialog(inventarioInterfaz, "¡No se encontró un producto para ver sus Lotes!",
                        "Exception!", JOptionPane.PLAIN_MESSAGE);
            }
        } else if (e.getSource().equals(volver)){
            inventarioInterfaz.volverMainMenu(0);
        }
    }

    private void mostrarInformacionFinancieraProducto() {
        if(producto != null){
            try {
                new InventarioInformacionFinancieraProducto(producto.getNombre(),
                        inventario.desempenoFinancieroProductoPorCodigo(producto.getCodigo()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(inventarioInterfaz, "¡No se encontró un producto para ver su Desempeño!",
                    "Exception!", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void cambiarImagenProducto() {
        if (producto != null){
            FileDialog fileDialog = new FileDialog(inventarioInterfaz, "Imagen", FileDialog.LOAD);
            fileDialog.setVisible(true);
            String newPath = fileDialog.getDirectory() + fileDialog.getFile();
            if (newPath != null){
                producto.setPathImagen(newPath);
                actualizarInformacion();
            }
        } else {
            JOptionPane.showMessageDialog(inventarioInterfaz, "¡No se encontró un producto para cambiar su imagen!",
                    "Exception!", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void buscarProductoPorCodigo() {
        Producto producto = inventario.getProductoPorCodigo(Integer.parseInt(codigoProducto.getText()));
        if (producto != null){
            setProducto(producto);
        } else {
            JOptionPane.showMessageDialog(inventarioInterfaz, "¡No se encontró un producto con ese código!",
                    "Exception!", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void buscarProductoPorNombre() {
        try {
            Producto producto = inventario.getProductoPorNombre(nombreProducto.getText());
            setProducto(producto);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(inventarioInterfaz, exception.getMessage(),
                    "Exception!", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
