package uk.ac.cam.group7.interaction_design.hiking_app;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Weather panel that shows up after we click on an location
 * Created by dobrik on 13.05.17.
 */
public class WeatherScreen extends JFrame{
    private JButton Back;
    private JCheckBox favourite;
    private JPanel WeatherPanel;
    private JLabel location;
    private JLabel sunrise;
    private JLabel sunset;
    private JButton tueButton;
    private JButton wedButton;
    private JList list1;
    private JLabel DangerLabel;
    private JButton thuButton;
    private JButton friButton;
    //protected MainScreen mainMenu;

    public WeatherScreen(Location _loc) {
        setTitle("blah");
        setSize(500,500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(WeatherPanel);
        location.setText(_loc.toString());
        favourite.setSelected(_loc.isFavourite());
        /**
         * Back button features
         */
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                MainScreen.getWindow().setVisible(true);
            }
        });
        this.setVisible(true);
        /**
         * make favourite features
         */
        favourite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _loc.setFavourite(_loc.isFavourite()^true);
                System.out.print(_loc.isFavourite());
            }
        });
    }

//    public static void main(String args[]) {
//        Location rns=new Location(1,2,"KypBa");
//        rns.setFavourite(true);
//        JFrame test=new WeatherScreen(rns);
//    }
}
