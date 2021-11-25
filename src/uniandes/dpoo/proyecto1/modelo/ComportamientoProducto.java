package uniandes.dpoo.proyecto1.modelo;

import uniandes.dpoo.proyecto1.exceptions.FechasCantidadesInconsistentesException;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ComportamientoProducto {
    private final ArrayList<Date> fechas;
    private final ArrayList<Float> cantidades;
    private final Producto producto;

    public ComportamientoProducto(ArrayList<Date> fechas, ArrayList<Float> cantidades, Producto producto)
            throws FechasCantidadesInconsistentesException {
        if (fechas.size() != cantidades.size())
            throw new FechasCantidadesInconsistentesException(fechas.size(), cantidades.size());
        else {
            this.fechas = fechas;
            this.cantidades = cantidades;
            this.producto = producto;
            registrarHoy();
        }
    }

    public void registrarHoy(){
        Date hoy = Calendar.getInstance().getTime();
        try {
            hoy = DateFormat.getDateInstance().parse(DateFormat.getDateInstance().format(hoy));
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        Date ultima = null;
        if (fechas.size() > 0){
            ultima = fechas.get(fechas.size()-1);
        }
        if (ultima == null || hoy.after(ultima)){
            fechas.add(hoy);
            cantidades.add(producto.getCantidadTotal());
        }
    }

    public ArrayList<Date> getFechas(){
        return fechas;
    }

    public ArrayList<Float> getCantidades(){
        return cantidades;
    }

    public Producto getProducto(){
        return producto;
    }

    public String lineaArchivo() {
        StringBuilder builder = new StringBuilder().append(producto.getCodigo());
        for (int i = 0; i < fechas.size(); i++) {
            builder.append(',').append(DateFormat.getDateInstance().format(fechas.get(i)))
                    .append(',').append(cantidades.get(i));
        }
        return builder.toString();
    }
}
