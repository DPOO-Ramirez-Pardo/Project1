package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.CantidadProducto;
import uniandes.dpoo.proyecto1.modelo.ProductoEmpaquetado;
import uniandes.dpoo.proyecto1.modelo.ProductoPorPeso;
import uniandes.dpoo.proyecto1.procesamiento.POS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class POSInformacionPedidoCantidadProducto extends JPanel implements ActionListener {
    private JTextField cantidad;
    private POS pos;
    private POSInterfaz posInterfaz;
    private CantidadProducto cantidadProducto;
    private JTextField precioTotalProducto;
    private POSPedidoMenu pedidoMenu;
    private JButton cambiarCantidad;
    private JButton eliminarProducto;

    private KeyAdapter numberOnly = new KeyAdapter() {
        public void keyPressed(KeyEvent ke) {
            if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == '.' || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                cantidad.setEditable(true);
            } else {
                cantidad.setEditable(false);
            }
        }
    };

    public POSInformacionPedidoCantidadProducto(POS pos, POSInterfaz posInterfaz, POSPedidoMenu pedidoMenu, CantidadProducto cantidadProducto) {
        this.pos = pos;
        this.posInterfaz = posInterfaz;
        this.pedidoMenu = pedidoMenu;
        this.cantidadProducto = cantidadProducto;
        JTextField nombreProducto = new JTextField();
        nombreProducto.setText(cantidadProducto.getProducto().getNombre());
        nombreProducto.setEditable(false);

        JTextField codigoProducto = new JTextField();
        codigoProducto.setText(Integer.toString(cantidadProducto.getProducto().getCodigo()));
        codigoProducto.setEditable(false);

        JLabel descripcionProducto = new JLabel("Descripcion: "+cantidadProducto.getProducto().getDescripcion());

        cantidad = new JTextField();
        cantidad.setText(Float.toString(cantidadProducto.getCantidad()));
        cantidad.addActionListener(this);
        cantidad.addKeyListener(numberOnly);

        JTextField condicionAlmacenamientoProducto = new JTextField();
        condicionAlmacenamientoProducto.setText(cantidadProducto.getProducto().mostrarCondicionAlmacenamiento());
        condicionAlmacenamientoProducto.setEditable(false);

        JTextField precioPorUnidadProducto = new JTextField();

        precioTotalProducto = new JTextField();
        try {
            precioTotalProducto.setText(Float.toString(cantidadProducto.getCosto()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        precioTotalProducto.setEditable(false);

        JPanel seccionSuperiorArriba = new JPanel();
        seccionSuperiorArriba.setLayout(new GridLayout(1, 4, 20, 0));
        seccionSuperiorArriba.add(new JLabel("Nombre:"));
        seccionSuperiorArriba.add(nombreProducto);
        seccionSuperiorArriba.add(new JLabel("Codigo"));
        seccionSuperiorArriba.add(codigoProducto);

        JPanel seccionSuperior = new JPanel();
        seccionSuperior.setLayout(new GridLayout(2,1,0,0));
        seccionSuperior.add(seccionSuperiorArriba);
        seccionSuperior.add(descripcionProducto);

        JPanel seccionIzquierda = new JPanel();
        seccionIzquierda.setLayout(new GridLayout(4, 2, 20, 10));
        seccionIzquierda.add(new JLabel("Cantidad:"));
        seccionIzquierda.add(cantidad);
        seccionIzquierda.add(new JLabel("Condición de Almacenamiento:"));
        seccionIzquierda.add(condicionAlmacenamientoProducto);
        if(cantidadProducto.getProducto() instanceof ProductoPorPeso){
            seccionIzquierda.add(new JLabel("Precio por "+cantidadProducto.getProducto().getUnidad()+":"));
            precioPorUnidadProducto.setText(Float.toString(cantidadProducto.getProducto().getPrecioPorUnidad()));
            precioPorUnidadProducto.setEditable(false);
        } else {
            seccionIzquierda.add(new JLabel("Precio por paquete:"));
            ProductoEmpaquetado productoEmpaquetado = ((ProductoEmpaquetado)cantidadProducto.getProducto());
            precioPorUnidadProducto.setText(Float.toString(productoEmpaquetado.getPrecioPorUnidad() * productoEmpaquetado.getPeso()));
            precioPorUnidadProducto.setEditable(false);
        }
        seccionIzquierda.add(precioPorUnidadProducto);
        seccionIzquierda.add(new JLabel("Precio Total:"));
        seccionIzquierda.add(precioTotalProducto);

        cambiarCantidad = new JButton("CAMBIAR CANTIDAD");
        cambiarCantidad.addActionListener(this);
        eliminarProducto = new JButton("ELIMINAR PRODUCTO");
        eliminarProducto.addActionListener(this);
        JPanel seccionAbajo = new JPanel();
        seccionAbajo.setLayout(new GridLayout(1,2,30,0));
        seccionAbajo.add(cambiarCantidad);
        seccionAbajo.add(eliminarProducto);

        JPanel seccionDerecha = new JPanel();
        seccionDerecha.setLayout(new BorderLayout());
        seccionDerecha.add(new JLabel("Imagen:"), BorderLayout.NORTH);
        seccionDerecha.add(new ImagenProducto(cantidadProducto.getProducto().getPathImagen()));

        add(seccionSuperior, BorderLayout.NORTH);
        add(seccionIzquierda, BorderLayout.WEST);
        add(seccionDerecha, BorderLayout.CENTER);
        add(seccionAbajo, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(cantidad) || e.getSource().equals(cambiarCantidad)){
            try {
                pos.agregarProductoPorCodigo(cantidadProducto.getProducto().getCodigo());
                pos.agregarCantidadProducto(Float.parseFloat(cantidad.getText()) - cantidadProducto.getCantidad());
                precioTotalProducto.setText(Float.toString(cantidadProducto.getCosto()));
                pedidoMenu.actualizarLista();
            } catch (Exception exception) {
                pos.cancelarProducto();
                JOptionPane.showMessageDialog(posInterfaz, exception.getMessage(),
                        "Exception!", JOptionPane.PLAIN_MESSAGE);
            }
        } else if (e.getSource().equals(eliminarProducto)){
            try {
                pos.eliminarProductoPorCodigo(cantidadProducto.getProducto().getCodigo());
                pedidoMenu.actualizarLista();
                pedidoMenu.eliminarProducto();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
