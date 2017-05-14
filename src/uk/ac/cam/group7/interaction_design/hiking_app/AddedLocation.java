package uk.ac.cam.group7.interaction_design.hiking_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class each containing a row for each location (those that are listed below)
 */
public class AddedLocation extends JPanel {

    /**
     * Constructor for such panel
     * @param currLoc
     * The location
     */
    public AddedLocation(Location currLoc) {
        this.setOpaque(false);
        this.setMaximumSize(new Dimension(750,40));

        JLabel jlabel = new JLabel(currLoc.toString(),JLabel.CENTER);
        jlabel.setFont(new Font("Courier New", Font.BOLD, 26));
        jlabel.setMinimumSize(new Dimension(0,60));
        jlabel.setForeground(Color.DARK_GRAY);

        MyButton opt = new MyButton("Options");
        opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {

                WeatherScreen currScreen=new WeatherScreen(currLoc);
                currScreen.setVisible(true);
                MainScreen.getWindow().setVisible(false);

            }
        });

        this.add(opt);
        this.add(Box.createRigidArea(new Dimension(25,0)));
        this.add(jlabel);


        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }


}
