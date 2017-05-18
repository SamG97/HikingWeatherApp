package uk.ac.cam.group7.interaction_design.hiking_app;

import org.bitpipeline.lib.owm.StatusWeatherData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JPanel mHourlyForecastPanel;
    private JPanel hack;
    private JScrollPane scrollPane;
    //protected MainScreen mainMenu;

    /**
     * Constructor for the weather screen
     *
     * @param _loc takes a location as a parameter
     */
    public WeatherScreen(Location _loc) {
        setTitle("blah");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
                _loc.toggleFavourite();
                //System.out.print(_loc.isFavourite());
            }
        });
        JPanel fff=new JPanel();
        mHourlyForecastPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mHourlyForecastPanel.setLayout(new BoxLayout(mHourlyForecastPanel,BoxLayout.Y_AXIS));
        //ForecastContainer fc=new ForecastContainer();
        //t
        //JScrollPane scroll=new JScrollPane(list = new JList(mStore.getPatternsNameSorted().toArray()));
        //list.addListSelectionListener(this);
        //patt.setLayout(new BorderLayout());
        //patt.add(scroll,BorderLayout.CENTER);

        for(int i=1;i<=2004;i++){
            fff=new JPanel();
            fff.setLayout(new FlowLayout());
            fff.setBorder(BorderFactory.createLineBorder(Color.green));
            fff.setSize(900,50);
            mHourlyForecastPanel.add(fff);
        }
        //JScrollPane scrollPane=new JScrollPane(mHourlyForecastPanel);
        //WeatherPanel.add(new JScrollPane(hack));
        //scrollPane.setPreferredSize(new Dimension(700,500));
        //scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //add(scrollPane);
        //WeatherPanel.add(mHourlyForecastPanel);
        //mHourlyForecastPanel.setLocation(150,150);

        //fc.addToRecent(_loc);
        //Coordinates: 42.7339 25.4858
        //StatusWeatherData swd=fc.getForecast(_loc);
        //System.out.println(swd.getDistance());

        //mHourlyForecast.setPreferredSize(new Dimension(700,700));
        //tss.add(new JButton("LLL"));
        //mHourlyForecast.add(new JLabel("LLLLL"));
        //mHourlyForecast.setPreferredSize(new Dimension(800,50));//like that?
        //mHourlyForecast.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //mHourlyForecast.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //mHourlyForecast.getViewport().setOpaque(false);
        //mHourlyForecast.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        //JList<String> test = new JList<String>(["ss", "g", "i"]);
    }
}
