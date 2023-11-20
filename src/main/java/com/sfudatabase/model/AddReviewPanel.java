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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.java.com.sfudatabase.controller.FunctionController;
import main.java.com.sfudatabase.controller.PanelController;

public class AddReviewPanel extends FunctionPanel {

    private FunctionController functionController;
    private PanelController panelController;
    private BufferedImage img;
    private JTextField userIDTextField;
    private JTextField busIDTextField;
    private JTextField starsTextField;
    private Boolean wasSearching;

    public AddReviewPanel(String imgPath, FunctionController functionController, PanelController panelController) {
        super(panelController, functionController);
        this.functionController = functionController;
        this.panelController = panelController;
        wasSearching = false;
        importBackground(imgPath);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        userIDTextField = new JTextField(15);
        busIDTextField = new JTextField(15);
        starsTextField = new JTextField(15);

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
        addReviewButton.addActionListener(e -> checkEntryValidity(inputsArray, wasSearching)); // Send this to a validator that checks if stars is a double then send to handleAddReview
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

        menuButton.removeActionListener(e -> panelController.showPanel("imagePanel"));
        menuButton.addActionListener(e -> goToMenu());
    }

    private void goToMenu() {
        busIDTextField.setText("");
        busIDTextField.setEnabled(true);
        starsTextField.setText("");

        panelController.showPanel("imagePanel");
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

    private void checkEntryValidity(ArrayList<JTextField> inputsArray, Boolean wasSearching) {
        if(inputsArray.get(0).getText().isEmpty() || inputsArray.get(1).getText().isEmpty() || inputsArray.get(2).getText().isEmpty()) {
            String errorMessage = "Error: Cannot leave any fields empty. Finish filling in the review before submitting.";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if(isDouble(inputsArray.get(2).getText())) {
                if(Double.parseDouble(inputsArray.get(2).getText()) < 1.00 || Double.parseDouble(inputsArray.get(2).getText()) > 5.00) {
                    String errorMessage = "Error: Invalid star entry. Please enter a decimal between 1 - 5 (eg. 2.5).";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    functionController.handleAddReview(inputsArray, wasSearching);
                }
            } else {
                String errorMessage = "Error: Invalid star entry. Please enter a decimal between 1 - 5 (eg. 2.5).";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public JTextField getUserIDTextField() {
        return userIDTextField;
    }

    public void setWasSearching(Boolean bool) {
        wasSearching = true;
    }

    public void setUserIDTextField(String userID) {
        userIDTextField.setText(userID);
        userIDTextField.setEnabled(false);
    }

    public void setBusIDTextField(String busID) {
        busIDTextField.setText(busID);
        busIDTextField.setEnabled(false);
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
