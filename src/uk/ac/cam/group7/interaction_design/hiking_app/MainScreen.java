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

    private static JFrame window;
    private static SearchPanel searchPanel;
    private static JPanel mainPanel;


    /**
     * Constructor for the MainScreen
     */
    public MainScreen() {
        window = new JFrame("Weather app");
        window.setPreferredSize(new Dimension(900,700));
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        searchPanel = new SearchPanel();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(700,700));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setOpaque(false);



        window.add(scrollPane,BorderLayout.CENTER);
        window.add(searchPanel,BorderLayout.NORTH);

        window.pack();
        window.setVisible(true);

        // Window Listeners
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);

            } //windowClosing
        } );


    }

    /**
     * static! getter for the window
     * so other panels when called can hide or update it;
     * @return
     */
    public static JFrame getWindow()
    {
        return window;
    }

    /**
     * ScrollField adder
     * @param input
     */
    public static void addScrollField(AddedLocation input)
    {
        mainPanel.add(input);
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



