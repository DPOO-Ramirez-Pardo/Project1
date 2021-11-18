package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.RecibosClienteMes;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.Calendar;

public class POSPanelMes extends JPanel {
    private RecibosClienteMes recibosClienteMes;

    public POSPanelMes(RecibosClienteMes recibosClienteMes){
        this.recibosClienteMes = recibosClienteMes;
        setPreferredSize(new Dimension(200,200));
    }

    @Override
    public void paint(Graphics g){
        if (recibosClienteMes != null){
            setPreferredSize(new Dimension(200,200));
            Calendar calendar = Calendar.getInstance();
            int mes = recibosClienteMes.getMes();
            int año = recibosClienteMes.getAño();
            try {
                calendar.setTime(DateFormat.getDateInstance()
                        .parse("01/"+String.format("%02d", mes)+"/"+año));
            } catch (ParseException exception) {
                exception.printStackTrace();
            }
            int indiceInicio = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int numero = getDiasMes(mes, año);
            int indiceFin = indiceInicio + numero;
            int filas = indiceFin / 7 + 1;
            int heightFecha = 200/filas;
            int widthFecha = 200/7;
            float cantidades[] = recibosClienteMes.getCantidadGastadaMes();
            for (int i = 0; i < filas * 7; i++){
                g.setColor(Color.BLACK);
                g.fillRect((i % 7)*widthFecha,(i / 7)*heightFecha,widthFecha,heightFecha);
                if (i >= indiceInicio && i < indiceFin){
                    if(cantidades[i - indiceInicio] == 0) g.setColor(Color.white);
                    else if (cantidades[i - indiceInicio] <= 10000) g.setColor(new Color(0,0,255/4));
                    else if (cantidades[i - indiceInicio] <= 50000) g.setColor(new Color(0,0,255/2));
                    else g.setColor(new Color(0,0,255*3/4));
                } else {
                    g.setColor(Color.gray);
                }
                g.fillRect((i % 7)*widthFecha+1,(i/7)*heightFecha+1,widthFecha-1,heightFecha-1);
            }
        } else {
            g.clearRect(0, 0, 200, 200);
            g.drawString("No disponible", 0, 100);
        }
    }

    private int getDiasMes(int mes, int año) {
        int numero = 0;
        if (mes == 2 && año % 4 == 0) numero = 29;
        else if (mes ==2) numero = 28;
        else if (mes == 4 || mes == 6 || mes ==9 || mes == 11) numero = 30;
        else numero = 31;
        return numero;
    }

    public void setRecibosClienteMes(RecibosClienteMes recibosClienteMes) {
        this.recibosClienteMes = recibosClienteMes;
        this.revalidate();
        this.repaint();
    }
}
