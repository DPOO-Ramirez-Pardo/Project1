package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Categoria;
import uniandes.dpoo.proyecto1.modelo.Cliente;
import uniandes.dpoo.proyecto1.modelo.Producto;
import uniandes.dpoo.proyecto1.modelo.Recibo;

import java.util.ArrayList;

public class POS {

    private Recibo actualRecibo;

    public POS(){
        actualRecibo = null;
    }

    public boolean tienePedido(){
        return (actualRecibo != null);
    }

    private void generarRecibo(){

    }

    private void registrarClienteEnSistemaPuntos(){

    }

    private void agregarProducto(){

    }

    private void eliminarProducto(){

    }

    private void iniciarPedido(){

    }

}
