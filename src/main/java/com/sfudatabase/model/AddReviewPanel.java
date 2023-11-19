package main.java.com.sfudatabase.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.java.com.sfudatabase.controller.FunctionController;
import main.java.com.sfudatabase.controller.PanelController;

public class AddReviewPanel extends FunctionPanel {

    FunctionController functionController;
    PanelController panelController;
    BufferedImage img;
    JTextField userIDTextField;

    public AddReviewPanel(String imgPath, FunctionController functionController, PanelController panelController) {
        super(panelController);
        this.functionController = functionController;
        this.panelController = panelController;
        importBackground(imgPath);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        userIDTextField = new JTextField(15);
        JTextField busIDTextField = new JTextField(15);
        JTextField starsTextField = new JTextField(15);

        ArrayList<JTextField> inputsArray = new ArrayList<>();
        inputsArray.add(userIDTextField);
        inputsArray.add(busIDTextField);
        inputsArray.add(starsTextField);

        addRow(functionPanel, gbc, "Reviewer's ID:", userIDTextField);
        addRow(functionPanel, gbc, "Business ID:", busIDTextField);
        addRow(functionPanel, gbc, "Stars:", starsTextField);

        // Glue
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        functionPanel.add(Box.createVerticalGlue(), gbc);

        // Button
        JButton addReviewButton = new JButton("Add Review");
        addReviewButton.addActionListener(e -> functionController.handleAddReview(inputsArray));
        addReviewButton.setBackground(Color.BLACK);
        addReviewButton.setForeground(Color.WHITE);
        addReviewButton.setPreferredSize(new Dimension(75, 25));
        addReviewButton.setBorder(new LineBorder(Color.WHITE, 2));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        functionPanel.add(addReviewButton, gbc);
    }

    private void addRow(RoundedPanel panel, GridBagConstraints gbc, String label, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel jLabel = new JLabel(label);
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);
    }

    public JTextField getUserIDTextField() {
        return userIDTextField;
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
