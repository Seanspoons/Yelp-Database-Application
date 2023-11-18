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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.java.com.sfudatabase.controller.FunctionController;

public class LoginPanel extends FunctionPanel {

    FunctionController functionController;
    BufferedImage img;

    public LoginPanel(String imgPath, FunctionController functionController) {
        this.functionController = functionController;
        importBackground(imgPath);

        // Label
        JLabel loginLabel = new JLabel("Login:");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);
        loginPanel.add(loginLabel, gbc);
        
        // Text field
        JTextField loginTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        loginPanel.add(loginTextField, gbc);

        // Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> functionController.handleLoginSubmit(loginTextField.getText()));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(75, 25));
        loginButton.setBorder(new LineBorder(Color.WHITE, 2));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);
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
