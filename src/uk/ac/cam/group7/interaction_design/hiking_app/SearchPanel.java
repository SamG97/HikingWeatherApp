package uk.ac.cam.group7.interaction_design.hiking_app;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
public class SearchPanel extends JPanel {

    private static HashSet<Location> locationStore=new HashSet<Location>();

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
                if(latitudeInputField.getText().equals("")||longtitudeInputField.getText().equals("")){
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
                curr=new Location(lat, longt, false, new Path() {
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