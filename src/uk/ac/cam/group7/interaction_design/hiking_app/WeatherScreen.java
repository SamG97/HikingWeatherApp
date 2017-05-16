package uk.ac.cam.group7.interaction_design.hiking_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Weather panel that shows up after we click on an location
 * Created by dobrik on 13.05.17.
 */
public class WeatherScreen extends JFrame {
    private JButton mBackButton;
    private JCheckBox mFav;
    private JPanel WeatherPanel;
    private JLabel location;
    private JLabel sunrise;
    private JLabel sunset;
    private JButton tueButton;
    private JButton wedButton;
    private JLabel DangerLabel;
    private JButton thuButton;
    private JButton friButton;
    private JScrollPane mHourlyForecast;
    private JPanel mHourlyForecastPanel;
    //protected MainScreen mainMenu;

    /**
     * Constructor for the weather screen
     *
     * @param _loc takes a location as a parameter
     */
    public WeatherScreen(Location _loc) {
        setTitle("blah");
        setSize(900, 700);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        add(WeatherPanel);
        location.setText(_loc.toString());
        mFav.setSelected(_loc.isFavourite());
        /**
         * Back button features
         */
        mBackButton.addActionListener(new ActionListener() {
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
        mFav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                _loc.setFavourite(!_loc.isFavourite());
                //System.out.print(_loc.isFavourite());
            }
        });
        List<JPanel> test = new LinkedList<JPanel>();
        for (int i = 1; i <= 100; i++) test.add(new JPanel(new GridLayout()));
        mHourlyForecast = new JScrollPane(new JList(test.toArray()));
        mHourlyForecastPanel.add(mHourlyForecast, BorderLayout.CENTER);

        //mHourlyForecast = new JScrollPane()
        //JList<String> test = new JList<String>(["ss", "g", "i"]);
    }

    //    public static void main(String args[]) {
//        Location rns=new Location(1,2,"KypBa");
//        rns.setFavourite(true);
//        JFrame test=new WeatherScreen(rns);
//    }
}
