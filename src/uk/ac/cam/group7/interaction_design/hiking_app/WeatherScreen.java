package uk.ac.cam.group7.interaction_design.hiking_app;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dobrik on 13.05.17.
 */
public class WeatherScreen extends JFrame{
    private JButton Back;
    private JCheckBox Favourite;
    private JPanel WeatherPanel;
    private JLabel Location;
    private JLabel Sunrise;
    private JLabel Sunset;
    private JButton tueButton;
    private JButton wedButton;
    private JList list1;
    private JLabel DangerLabel;

    public WeatherScreen() {
        setTitle("blah");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(WeatherPanel);
        //pack();I
    }

    public static void main(String args[]) {
        JFrame test=new WeatherScreen();
        test.setVisible(true);
    }
}
