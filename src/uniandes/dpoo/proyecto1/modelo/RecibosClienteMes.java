package uniandes.dpoo.proyecto1.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class RecibosClienteMes {
    private ArrayList<Recibo> recibosMes;
    private float cantidadGastadaMes[];

    public RecibosClienteMes(Cliente cliente, int mes, int año) {
        ArrayList<Recibo> recibos = cliente.getRecibos();
        recibosMes = new ArrayList<>();

        int numero = 0;
        if (mes == 2 && año % 4 == 0) numero = 29;
        else if (mes ==2) numero = 28;
        else if (mes == 4 || mes == 6 || mes ==9 || mes == 11) numero = 30;
        else numero = 31;
        cantidadGastadaMes = new float[numero];
        for (int i = 0; i < numero; i++) {
            cantidadGastadaMes[i] = 0;
        }

        String inicioString = "01/"+String.format("%02d",mes)+"/"+año;
        String finalString = null;
        if (mes == 12) finalString = "01/01/"+Integer.toString(año+1);
        else finalString = "01/"+String.format("%02d",mes+1)+"/"+año;
        Date inicioMes = null;
        Date finalMes = null;
        try {
            inicioMes = DateFormat.getDateInstance().parse(inicioString);
            finalMes = DateFormat.getDateInstance().parse(finalString);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        for (Recibo recibo: recibos){
            Date fecha = recibo.getFecha();
            int dia = Integer.parseInt(DateFormat.getDateInstance().format(fecha).split("/")[0])- 1;
            if((fecha.after(inicioMes) && fecha.before(finalMes)) || fecha.equals(inicioMes)){
                recibosMes.add(recibo);
                cantidadGastadaMes[dia] += recibo.getTotal();
            }
        }


    }
}
