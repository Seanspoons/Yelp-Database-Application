package main.java.com.sfudatabase.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import main.java.com.sfudatabase.controller.*;

public class ImagePanel extends JPanel {
    
    // Need to add buttons to finish class
    /*
     * example
     * JButton loginButton = new JButton("Login");
     * button.addActionListener(e -> controller.handleLoginClick());)
     */
    private ImagePanelController controller;
    private BufferedImage img;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem loginItem;
    private JMenuItem businessSearchItem;
    private JMenuItem userSearchItem;
    private JMenuItem addFriendItem;
    private JMenuItem addReviewItem;


    public ImagePanel(String imgPath, ImagePanelController controller) {
        this.controller = controller;
        importBackground(imgPath);
        createMenuElements();
        addActionListeners();
    }

    private void importBackground(String imgPath) {
        try{
            this.img = ImageIO.read(new File(imgPath));
        } catch (IOException e){
            throw new Error("Error");
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
        addFriendItem = new JMenuItem("Add Friend");
        addFriendItem.setEnabled(false);
        addReviewItem = new JMenuItem("Add Review Search");
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
