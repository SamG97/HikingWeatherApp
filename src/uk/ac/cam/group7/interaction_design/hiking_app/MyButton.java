package uk.ac.cam.group7.interaction_design.hiking_app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;

/**
 * Decorator class for the Send Button
 */
public class MyButton extends JButton {

    private String mName;
    public  MyButton(String text) {

        super(text);
        mName=text;
        this.setContentAreaFilled(false);


    }
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor( Color.LIGHT_GRAY.brighter() );
        g.fillRect(0, 0, getSize().width, getSize().height);
        super.paintComponent(g);
    }
}

