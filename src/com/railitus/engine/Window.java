package com.railitus.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    public JFrame getFrame() {
        return frame;
    }

    private JFrame frame;

    public BufferedImage getImage() {
        return image;
    }

    private BufferedImage image;

    public Canvas getCanvas() {
        return canvas;
    }

    private Canvas canvas;
    private BufferStrategy buffStrgy;
    private Graphics graphics;


    public Window(GameContainer gc){
        image = new BufferedImage(gc.getWidth(),gc.getHeight(), BufferedImage.TYPE_INT_RGB);

        canvas = new Canvas();
        Dimension dim = new Dimension((int)(gc.getWidth()*gc.getScale()),(int)(gc.getHeight()*gc.getScale()));
        canvas.setPreferredSize(dim);
        canvas.setMaximumSize(dim);
        canvas.setMinimumSize(dim);

        frame = new JFrame(gc.getTittle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas,BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        buffStrgy = canvas.getBufferStrategy();
        graphics = buffStrgy.getDrawGraphics();
    }

    public void update(){
        graphics.drawImage(image,0,0, canvas.getWidth(), canvas.getHeight(), null);
        buffStrgy.show();
    }
}
