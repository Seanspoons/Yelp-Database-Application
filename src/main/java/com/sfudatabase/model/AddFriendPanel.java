package main.java.com.sfudatabase.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import main.java.com.sfudatabase.controller.FunctionController;
import main.java.com.sfudatabase.controller.PanelController;

public class AddFriendPanel extends FunctionPanel {

    FunctionController functionController;
    PanelController panelController;
    BufferedImage img;

    public AddFriendPanel(String imgPath, FunctionController functionController, PanelController panelController) {
        super(panelController);
        this.functionController = functionController;
        this.panelController = panelController;
        importBackground(imgPath);

        // Label
        JLabel addFriendLabel = new JLabel("Add Friend (By ID):");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        functionPanel.add(addFriendLabel, gbc);
        
        // Text field
        JTextField addFriendTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        functionPanel.add(addFriendTextField, gbc);

        // Glue
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        functionPanel.add(Box.createVerticalGlue(), gbc);

        // Button
        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.addActionListener(e -> functionController.handleAddFriend(addFriendTextField));
        addFriendButton.setBackground(Color.BLACK);
        addFriendButton.setForeground(Color.WHITE);
        addFriendButton.setPreferredSize(new Dimension(75, 25));
        addFriendButton.setBorder(new LineBorder(Color.WHITE, 2));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        functionPanel.add(addFriendButton, gbc);
    }

    private void importBackground(String imgPath) {
        try{
            this.img = ImageIO.read(new File(imgPath));
        } catch (IOException e){
            throw new Error("Error");
        }
    }

    @Override
    protected  void paintComponent(Graphics g){
        super.paintComponent(g);
        if (img != null){
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
}
