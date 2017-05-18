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

    private static JFrame sWindow;
    private static SearchPanel sSearchPanel;
    private static JPanel sMainPanel;


    /**
     * Constructor for the MainScreen
     */
    public MainScreen() {
        sWindow = new JFrame("Weather app");
        sWindow.setPreferredSize(new Dimension(900,700));
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
        scrollPane.requestFocusInWindow(); //Hack to make the Latitude textfield unfocused
        sWindow.setVisible(true);

        // sWindow Listeners
        sWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            } //windowClosing
        } );


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
    public static void addScrollField(AddedLocation input)
    {
        sMainPanel.add(input);
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


    public static void main(String[] args) {
        MainScreen app = new MainScreen();
    }


}



