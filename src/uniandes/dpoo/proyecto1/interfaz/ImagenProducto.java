package uniandes.dpoo.proyecto1.interfaz;

import uniandes.dpoo.proyecto1.modelo.Producto;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagenProducto extends JPanel {
    private String path;
    private Image image;

    public ImagenProducto(String path){
        this.path = path;
        try {
            image = ImageIO.read(new File(path));
            System.out.println("Imagen leida");
        } catch (IOException e) {
            image = null;
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        setPreferredSize(new Dimension(200,200));
    }

    public ImagenProducto() {
        this.path = null;
        image = null;
        setPreferredSize(new Dimension(200,200));
    }

    public void setPath(String path) {
        this.path = path;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            image = null;
        }
        repaint();
    }

    @Override
    public void paint(Graphics g){
        if (image != null){
            setPreferredSize(new Dimension(200,200));
            g.drawImage(image,0,0,200,200,this);
        } else {
            g.clearRect(0, 0, 200, 200);
            g.drawString("No disponible", 0, 100);
        }
    }
}
