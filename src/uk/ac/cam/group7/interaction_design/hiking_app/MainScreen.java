package uk.ac.cam.group7.interaction_design.hiking_app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * This is the main screen that will have search menus etc
 * @author Alex
 */
class MainScreen  {
	
   private static ForecastContainer sForecastContainer= new ForecastContainer();
   
    /** Specify the look and feel to use by defining the LOOKANDFEEL constant */
   
    private static JFrame sWindow;
    private static SearchPanel sSearchPanel;
    private static JPanel sMainPanel;


    /**
     * Constructor for the MainScreen
     */
    
       private MainScreen() {
    	
        sWindow = new JFrame("Weather app");
        sWindow.setPreferredSize(new Dimension(900,700));
        sWindow.setLocation(240, 30);
        sWindow.setResizable(false);
        sWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        sSearchPanel = new SearchPanel();
        sMainPanel = new JPanel();
        sMainPanel.setLayout(new BoxLayout(sMainPanel, BoxLayout.PAGE_AXIS));
       
        JScrollPane scrollPane = new JScrollPane(sMainPanel);
        scrollPane.setPreferredSize(new Dimension(700,700));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setOpaque(false);

        sWindow.add(scrollPane,BorderLayout.CENTER);
        sWindow.add(sSearchPanel,BorderLayout.NORTH);
        sWindow.pack();
        sWindow.setVisible(true);

        // sWindow Listeners
        sWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            } //windowClosing
        } );
        
        
        // initialize the main screen with the saved (recent) locations 
        refreshMain();
              
    }
    
    /**
     * static! getter for the window
     * so other panels when called can hide or update it;
     * @return
     * window returned
     */
    public static JFrame getWindow()
    {
        return sWindow;
    }

    /**
     * ScrollField adder
     * @param input
     * the location we are adding
     */
    public static void addNewLocation(AddedLocation input)
    {
        sMainPanel.add(input);  
        sWindow.setVisible(true);
        sForecastContainer.addToRecent(input.getLoc());
    }
  
    
    /** updating the main panel when new input from the API is recieved
    * to be called when an update session is done 
    */
    
    private void refreshMain()
    {
    	 
        for(Location recentloc: sForecastContainer.getRecent())
        {
        	sMainPanel.add(new AddedLocation(recentloc));
        }
        sWindow.setVisible(true);
    }
    
    /**
     * Add a border to a component
     * @param component
     * Component to get the border
     * @param title
     * Title of border
     */
    public static void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch,title);
        component.setBorder(tb);
    }
    
    /** remove a location from the saved list  */
    public static void removeLoc(AddedLocation location)
    {
    	
    	sMainPanel.remove(location);
    	sWindow.revalidate();
    	sWindow.repaint();
    	sForecastContainer.removeLocation(location.getLoc());   	
    }
    
    /** rename a location from the saved list */
    public static void renameLoc(AddedLocation location, String name)
    {
    	location.setText(name);
    	sWindow.revalidate();
    	sWindow.repaint();
    	 	
    }
    
    /** check if a location is already saved*/
    public static boolean contains(Location location)
    {
    	return sForecastContainer.getRecent().contains(location);
    }


    /** starting point of the application which creates the main screen*/
    private static void createAndShowGUI() {	
    	/**Set the look and feel */
        JFrame.setDefaultLookAndFeelDecorated(true);
        //Create and set up the window.
       MainScreen app = new MainScreen();
       
    }


    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
   


}
