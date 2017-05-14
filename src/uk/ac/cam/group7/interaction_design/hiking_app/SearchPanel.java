package uk.ac.cam.group7.interaction_design.hiking_app;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.TreeSet;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * SearchPanel for latitude and longtitude
 * @author Alex
 */
public class SearchPanel extends JPanel {

    private static TreeSet<Location> locationStore=new TreeSet<Location>();

    /**
     * constructor
     */
    public SearchPanel(){

        MainScreen.addBorder(this,"Search Panel");
        this.setPreferredSize(new Dimension(700,60));


        JTextField latitudeInputField = new JTextField("type here");
        JTextField longtitudeInputField = new JTextField("type here");
        JButton searchButton = new MyButton("Search"); // search button
        searchButton.setEnabled(false);// when there is not text the button is disabled




        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(latitudeInputField);
        this.add(longtitudeInputField);
        this.add(searchButton);

        // enable/disable the search button depending on the text in the fields
        latitudeInputField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent event) {
                String content = latitudeInputField.getText();
                if (!content.equals("")) {
                    searchButton.setEnabled(true);

                } else {
                    searchButton.setEnabled(false);

                }
            }
        });

        /**
         * ActionListener on the searchButton that does sanitisation on the input
         * it could be only double or int
         * (FIXME Code duplication a bit)
         * (c) Dobrik
         */
        searchButton.addActionListener(new ActionListener() {
            double longt=0;
            double lat=0;
            @Override
            public void actionPerformed(ActionEvent event) {
                if(latitudeInputField.getText()==""||longtitudeInputField.getText()==""){
                    JOptionPane.showMessageDialog(null, "Please fill both fields");
                    return;
                }
                if(!latitudeInputField.getText().equals("")){
                    String latS=latitudeInputField.getText();
                    try{
                        lat=Double.parseDouble(latS);
                    }
                    catch (NumberFormatException e) {
                        try{
                            lat=Integer.parseInt(latS);
                        }
                        catch (NumberFormatException e2){
                            JOptionPane.showMessageDialog(null, "Not a valid coordinate");
                            latitudeInputField.setText("");
                            return;
                        }
                    }

                }

                if(!longtitudeInputField.getText().equals("")){
                    String longtS=longtitudeInputField.getText();
                    try{
                        longt=Double.parseDouble(longtS);
                    }
                    catch (NumberFormatException e) {
                        try{
                            longt=Integer.parseInt(longtS);
                        }
                        catch (NumberFormatException e2){
                            JOptionPane.showMessageDialog(null, "Not a valid coordinate");
                            latitudeInputField.setText("");
                            return;
                        }
                    }
                }
                Location curr;
                locationStore.clear();
                locationStore.add(curr=new Location(lat,longt));
                MainScreen.addScrollField(new AddedLocation(curr));
                MainScreen.getWindow().setVisible(true);

                //MyFrame.addScrollField(new AddedLocation(message));
                //MyFrame.getWindow().setVisible(true);
            }
        });


    }


}