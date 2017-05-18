package uk.ac.cam.group7.interaction_design.hiking_app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class each containing a row for each location (those that are listed below)
 */
public class AddedLocation extends JPanel {

     private final Location mLoc;
  private final  AddedLocation test;
   private final JLabel locName;
    /**
     * Constructor for such panel
     * @param currLoc
     * The location
     */
    public AddedLocation(Location currLoc) {
        test=this;
        mLoc=currLoc;
        this.setOpaque(false);
        this.setMaximumSize(new Dimension(750,40));

        JLabel locName= new JLabel(currLoc.toString(),JLabel.CENTER);
        locName.setFont(new Font("Courier New", Font.BOLD, 26));
        locName.setMinimumSize(new Dimension(0,60));
        locName.setForeground(Color.DARK_GRAY);
        locName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                WeatherScreen currScreen=new WeatherScreen(currLoc);
                currScreen.setVisible(true);
                MainScreen.getWindow().setVisible(false);
            }
        });

       MyButton opt = new MyButton("Options");
        opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
               OptionsMenu.createToday(test);
            }
        });

        this.add(opt);
        this.add(Box.createRigidArea(new Dimension(25,0)));
        this.add(locName);


        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
     public Location getLoc()
    {
		return mLoc;
    	
    }
    public void setText(String input)
    {
    	mLoc.setName(input);
    	locName.setText(input);
    }


}
