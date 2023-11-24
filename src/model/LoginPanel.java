package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.FunctionController;
import controller.PanelController;

public class LoginPanel extends FunctionPanel {

    FunctionController functionController;
    PanelController panelController;
    private BufferedImage img;

    public LoginPanel(String imgPath, FunctionController functionController, PanelController panelController) {
        super(panelController, functionController);
        this.functionController = functionController;
        this.panelController = panelController;
        importBackground(imgPath);

        // Label
        JLabel loginLabel = new JLabel("Login:");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 0, 10);
        functionPanel.add(loginLabel, gbc);
        
        // Text field
        JTextField loginTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        functionPanel.add(loginTextField, gbc);

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
        functionPanel.add(loginButton, gbc);
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
    		System.out.println("Error stream is null in LoginPanel");
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
