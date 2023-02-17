package pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.GUI;

import pl.edu.pw.mini.charuzaalepiankal.ZPOIFproject.ingredient_holder.Fridge;

import javax.swing.*;

import java.awt.*;
import java.io.File;

public class GUI extends JFrame {

    private Fridge fridge = new Fridge();
    private static int WINDOW_WIDTH = 1200;
    private static int WINDOW_HEIGHT = 800;
    private static Dimension WINDOW_DIMENSION = new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT);
    private JPanel searchPanel;
    private JPanel fridgePanel = new FridgePanel(fridge);
    private JPanel listPanel = new ShoppingListPanel();

    public GUI() {
    	this.searchPanel = new SearchPanel(((ShoppingListPanel) listPanel).getList(), fridge);

    	com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme.setup();

        setTitle("Recipe Browser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_DIMENSION);
        setPreferredSize(WINDOW_DIMENSION);
        getContentPane().setBackground(new Color(0xFFFFFF));
        ImageIcon mainIcon = new ImageIcon("icons" + File.separator + "main_icon.png");
        setIconImage(mainIcon.getImage());
        
        // tabs
        UIManager.put( "TabbedPane.showTabSeparators", true );
        JTabbedPane tabbedPanel = new JTabbedPane();
        
        ImageIcon tab1Icon = new ImageIcon(new ImageIcon("icons" + File.separator + "search.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon tab2Icon = new ImageIcon(new ImageIcon("icons" + File.separator + "fridge.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon tab3Icon = new ImageIcon(new ImageIcon("icons" + File.separator + "cart.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        
        tabbedPanel.addTab("Search", tab1Icon, searchPanel);
        tabbedPanel.addTab("Your Fridge", tab2Icon, fridgePanel);
        tabbedPanel.addTab("Your Shopping List", tab3Icon, listPanel);
        
        tabbedPanel.addChangeListener(e -> {
        	((ShoppingListPanel) listPanel).refresh();

        });

        getContentPane().add(tabbedPanel);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
    }


}

