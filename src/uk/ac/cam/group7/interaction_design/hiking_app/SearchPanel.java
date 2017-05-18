package uk.ac.cam.group7.interaction_design.hiking_app;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.nio.file.Path;

/**
 * SearchPanel for latitude and longtitude
 * @author Alex
 */
public class SearchPanel extends JPanel{

    private static HashSet<Location> locationStore=new HashSet<Location>();

    /**
     * constructor
     */
    public SearchPanel(){

        MainScreen.addBorder(this,"Search Panel");
        this.setPreferredSize(new Dimension(700,60));


        JTextField latitudeInputField = new JTextField("Latitude");
        JTextField longitudeInputField = new JTextField("Longitude");
        JButton searchButton = new MyButton("Search"); // search button
        searchButton.setEnabled(true);// when there is not text the button is disabled
        //Leave it enabled, input sanitization will catch it

        latitudeInputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(latitudeInputField.getText().equals("Latitude")) {
                    latitudeInputField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (latitudeInputField.getText().equals("")){
                    latitudeInputField.setText("Latitude");
                }
            }
        });
        longitudeInputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if( longitudeInputField.getText().equals("Longitude") ) {
                    longitudeInputField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if( longitudeInputField.getText().equals("")) {
                    longitudeInputField.setText("Longitude");
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(latitudeInputField);
        this.add(longitudeInputField);
        this.add(searchButton);


        /**
         * ActionListener on the searchButton that does sanitisation on the input
         * it could be only double or int
         * (FIXME Code duplication a bit)
         * (c) Dobrik
         */
        searchButton.addActionListener(new ActionListener() {
            float longt=0;
            float lat=0;
            @Override
            public void actionPerformed(ActionEvent event) {
                if(latitudeInputField.getText().equals("")||longitudeInputField.getText().equals("")){
                    //This shouldn't happen with the new dynamic areas
                    JOptionPane.showMessageDialog(null, "Please fill both fields");
                    return;
                }
                if(!latitudeInputField.getText().equals("")){
                    String latS=latitudeInputField.getText();
                    try{
                        lat=Float.parseFloat(latS);
                    }
                    catch (NumberFormatException e) {
                        try{
                            lat=Integer.parseInt(latS);
                        }
                        catch (NumberFormatException e2){
                            JOptionPane.showMessageDialog(null, "Not a valid coordinate");
                            latitudeInputField.setText("Latitude");
                            longitudeInputField.setText("Longitude");
                            return;
                        }
                    }

                }

                if(!longitudeInputField.getText().equals("")){
                    String longtS=longitudeInputField.getText();
                    try{
                        longt=Float.parseFloat(longtS);
                    }
                    catch (NumberFormatException e) {
                        try{
                            longt=Integer.parseInt(longtS);
                        }
                        catch (NumberFormatException e2){
                            JOptionPane.showMessageDialog(null, "Not a valid coordinate");
                            latitudeInputField.setText("Latitude");
                            longitudeInputField.setText("Longitude");
                            return;
                        }
                    }
                }
                Location curr = new Location(lat, longt);
                //TODO: Fix the commented out code below as it's preventing compilation
                /*curr=new Location(lat, longt) {
                    @Override
                    public FileSystem getFileSystem() {
                        return null;
                    }

                    @Override
                    public boolean isAbsolute() {
                        return false;
                    }

                    @Override
                    public Path getRoot() {
                        return null;
                    }

                    @Override
                    public Path getFileName() {
                        return null;
                    }

                    @Override
                    public Path getParent() {
                        return null;
                    }

                    @Override
                    public int getNameCount() {
                        return 0;
                    }

                    @Override
                    public Path getName(int i) {
                        return null;
                    }

                    @Override
                    public Path subpath(int i, int i1) {
                        return null;
                    }

                    @Override
                    public boolean startsWith(Path path) {
                        return false;
                    }

                    @Override
                    public boolean startsWith(String s) {
                        return false;
                    }

                    @Override
                    public boolean endsWith(Path path) {
                        return false;
                    }

                    @Override
                    public boolean endsWith(String s) {
                        return false;
                    }

                    @Override
                    public Path normalize() {
                        return null;
                    }

                    @Override
                    public Path resolve(Path path) {
                        return null;
                    }

                    @Override
                    public Path resolve(String s) {
                        return null;
                    }

                    @Override
                    public Path resolveSibling(Path path) {
                        return null;
                    }

                    @Override
                    public Path resolveSibling(String s) {
                        return null;
                    }

                    @Override
                    public Path relativize(Path path) {
                        return null;
                    }

                    @Override
                    public URI toUri() {
                        return null;
                    }

                    @Override
                    public Path toAbsolutePath() {
                        return null;
                    }

                    @Override
                    public Path toRealPath(LinkOption... linkOptions) throws IOException {
                        return null;
                    }

                    @Override
                    public File toFile() {
                        return null;
                    }

                    @Override
                    public WatchKey register(WatchService watchService, WatchEvent.Kind<?>[] kinds, WatchEvent.Modifier... modifiers) throws IOException {
                        return null;
                    }

                    @Override
                    public WatchKey register(WatchService watchService, WatchEvent.Kind<?>[] kinds) throws IOException {
                        return null;
                    }

                    @Override
                    public Iterator<Path> iterator() {
                        return null;
                    }

                    @Override
                    public int compareTo(Path path) {
                        return 0;
                    }
                }, "GG");
                */
                //
                if(locationStore.contains(curr)){
                    JOptionPane.showMessageDialog(null, "Can't duplicate loctations");
                    return;
                }
                locationStore.add(curr);
                MainScreen.addScrollField(new AddedLocation(curr));
                MainScreen.getWindow().setVisible(true);

                //MyFrame.addScrollField(new AddedLocation(message));
                //MyFrame.getWindow().setVisible(true);
            }
        });
    }
}