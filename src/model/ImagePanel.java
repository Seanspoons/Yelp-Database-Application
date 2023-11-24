package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import controller.ImagePanelController;

public class ImagePanel extends JPanel {
    
    private ImagePanelController controller;
    private BufferedImage img;
    private JMenuBar menuBar;
    private JMenu menu;
    public static JMenuItem loginItem;
    public static JMenuItem businessSearchItem;
    public static JMenuItem userSearchItem;
    public static JMenuItem addFriendItem;
    public static JMenuItem addReviewItem;

    public ImagePanel(String imgPath, ImagePanelController controller) {
        this.controller = controller;
        importBackground(imgPath);
        createMenuElements();
        addActionListeners();
    }

    private void importBackground(String imgPath) {
    	InputStream stream = AddFriendPanel.class.getResourceAsStream(imgPath);
    	
    	if(stream != null) {
    		try {
    			img = ImageIO.read(stream);
    		} catch (IOException e) {
    			e.printStackTrace();
    		} finally {
    			try {
    				stream.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	} else {
    		// Error
    		System.out.println("Error stream is null in ImagePanel");
    	}
    }

    private void createMenuElements() {
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.black);
        Border menuBorder = new LineBorder(Color.WHITE, 2);
        menuBar.setBorder(menuBorder);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Need to fix the constraints to center the JMenuBar
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        menu = new JMenu("Options");
        menu.setForeground(Color.white);
        loginItem = new JMenuItem("Login");
        businessSearchItem = new JMenuItem("Business Search");
        businessSearchItem.setEnabled(false);
        userSearchItem = new JMenuItem("User Search");
        userSearchItem.setEnabled(false);
        addFriendItem = new JMenuItem("Add a Friend");
        addFriendItem.setEnabled(false);
        addReviewItem = new JMenuItem("Review a Business");
        addReviewItem.setEnabled(false);

        this.add(menuBar);
        menuBar.add(menu);
        menu.add(loginItem);
        menu.add(businessSearchItem);
        menu.add(userSearchItem);
        menu.add(addFriendItem);
        menu.add(addReviewItem);
    }

    private void addActionListeners() {
        loginItem.addActionListener(e -> controller.handleLoginClick());
        businessSearchItem.addActionListener(e -> controller.handleBusinessSearchClick());
        userSearchItem.addActionListener(e -> controller.handleUserSearchClick());
        addFriendItem.addActionListener(e -> controller.handleAddFriendClick());
        addReviewItem.addActionListener(e -> controller.handleAddReviewClick());
    }

    @Override
    protected  void paintComponent(Graphics g){
        super.paintComponent(g);
        if (img != null){
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
