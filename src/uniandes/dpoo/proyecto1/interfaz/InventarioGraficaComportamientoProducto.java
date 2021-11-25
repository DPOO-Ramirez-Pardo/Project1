package uniandes.dpoo.proyecto1.interfaz;

import org.knowm.xchart.*;
import uniandes.dpoo.proyecto1.modelo.ComportamientoProducto;
import uniandes.dpoo.proyecto1.modelo.ProductoEmpaquetado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InventarioGraficaComportamientoProducto extends JFrame implements ActionListener {
    private CategoryChart chart;
    private JButton guardarGraficaButton;
    public InventarioGraficaComportamientoProducto(ComportamientoProducto comportamientoProducto){
        setTitle("Comportamiento");
        setLayout(new BorderLayout());
        setSize(600,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chart = new CategoryChartBuilder().width(600).height(400)
                .title("Comportamiento "+comportamientoProducto.getProducto().getNombre()).xAxisTitle("Fechas")
                .yAxisTitle("Cantidades").build();
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Bar);
        chart.getStyler().setDatePattern("DD/MM/YYYY");
        String unidad = comportamientoProducto.getProducto().getUnidad();
        if (comportamientoProducto.getProducto() instanceof ProductoEmpaquetado) unidad = "Paquetes";
        chart.addSeries(unidad,
                comportamientoProducto.getFechas(),comportamientoProducto.getCantidades());
        JPanel chartPanel = new XChartPanel<CategoryChart>(chart);
        add(chartPanel, BorderLayout.NORTH);

        guardarGraficaButton = new JButton("GUARDAR GRÁFICA");
        guardarGraficaButton.addActionListener(this);
        add(guardarGraficaButton, BorderLayout.SOUTH);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(guardarGraficaButton)){
            FileDialog fileDialog = new FileDialog(this, "GUARDAR GRÁFICA", FileDialog.SAVE);
            fileDialog.setVisible(true);
            String path = fileDialog.getDirectory() + fileDialog.getFile();
            if (fileDialog.getDirectory() != null){
                try {
                    BitmapEncoder.saveBitmap(chart, path, BitmapEncoder.BitmapFormat.PNG);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(this, exception.getMessage(),
                            "¡Error!", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }
}
