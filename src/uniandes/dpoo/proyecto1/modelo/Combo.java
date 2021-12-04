package uniandes.dpoo.proyecto1.modelo;

import java.util.*;

public class Combo extends Promocion{
    private HashMap<Producto, Integer> cantidadesCombo;
    private float porcentaje;
    private String nombre;
    private float costo;

    public Combo(String id, Date inicio, Date vencimiento, HashMap<Producto, Integer> cantidadesCombo, float porcentaje, String nombre) {
        super(id, inicio, vencimiento);
        this.cantidadesCombo = cantidadesCombo;
        this.porcentaje = porcentaje;
        this.nombre = nombre;
        this.costo = calcularCosto();
    }

    private float calcularCosto() {
        costo += 0;
        for (Producto producto: cantidadesCombo.keySet()){
            try {
                costo += producto.precioProductos(cantidadesCombo.get(producto));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return costo;
    }

    @Override
    public int aplicarPuntos(Recibo recibo) {
        return 0;
    }

    @Override
    public float aplicarDescuento(Recibo recibo) {
        int combos = getNumeroCombos(recibo);
        return combos * costo * porcentaje;
    }

    private int getNumeroCombos(Recibo recibo) {
        int combos = -1;
        for(CantidadProducto cantidadProducto: recibo.getCantidadesProductos()){
            if (cantidadesCombo.containsKey(cantidadProducto.getProducto())) {
                int posiblesCombos = (int)(cantidadProducto.getCantidad()) / cantidadesCombo.get(cantidadProducto.getProducto());
                if (combos == -1){
                    combos = posiblesCombos;
                } else if (posiblesCombos < combos){
                    combos = posiblesCombos;
                }
            }
        }
        return combos;
    }

    @Override
    public String lineaRecibo(Recibo recibo) {
        int combos = getNumeroCombos(recibo);
        return combos + " " + nombre + ": -" + (combos * costo * porcentaje);
    }

    @Override
    protected boolean cumpleRequisitosAdicionales(Recibo recibo) {
        HashMap<Producto, Boolean> estaProducto = new HashMap<>();
        for (Producto producto: cantidadesCombo.keySet()){
            estaProducto.put(producto, false);
        }
        for(CantidadProducto cantidadProducto: recibo.getCantidadesProductos()){
            if (estaProducto.containsKey(cantidadProducto.getProducto()))
                estaProducto.put(cantidadProducto.getProducto(),
                        cantidadProducto.getCantidad() >= cantidadesCombo.get(cantidadProducto.getProducto()));
        }
        for(Boolean bProducto: estaProducto.values()){
            if (!bProducto) return false;
        }
        return true;
    }

    @Override
    public Collection<Producto> getProductos() {
        return cantidadesCombo.keySet();
    }
}
