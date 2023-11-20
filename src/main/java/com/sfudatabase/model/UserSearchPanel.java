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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.com.sfudatabase.controller.FunctionController;
import main.java.com.sfudatabase.controller.PanelController;

public class UserSearchPanel extends FunctionPanel {

    private FunctionController functionController;
    PanelController panelController;
    private BufferedImage img;
    
    public UserSearchPanel(String imgPath, FunctionController functionController, PanelController panelController) {
        super(panelController, functionController);
        this.functionController = functionController;
        this.panelController = panelController;
        importBackground(imgPath);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField userNameTextField = new JTextField(15);
        JTextField revCountTextField = new JTextField(15);
        JTextField avgStarsTextField = new JTextField(15);

        JCheckBox userNameCheckBox = new JCheckBox();
        JCheckBox revCountCheckBox = new JCheckBox();
        JCheckBox avgStarsCheckBox = new JCheckBox();

        userNameCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(userNameCheckBox.isSelected()) {
                    userNameTextField.setEnabled(true);
                } else {
                    userNameTextField.setEnabled(false);
                    userNameTextField.setText("");
                }
            }
        });
        revCountCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(revCountCheckBox.isSelected()) {
                    revCountTextField.setEnabled(true);
                } else {
                    revCountTextField.setEnabled(false);
                    revCountTextField.setText("");
                }
            }
        });
        avgStarsCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(avgStarsCheckBox.isSelected()) {
                    avgStarsTextField.setEnabled(true);
                } else {
                    avgStarsTextField.setEnabled(false);
                    avgStarsTextField.setText("");
                }
            }
        });

        addRow(functionPanel, gbc, "User's name:", userNameTextField, userNameCheckBox);
        addRow(functionPanel, gbc, "Review Count:", revCountTextField, revCountCheckBox);
        addRow(functionPanel, gbc, "Average Stars:", avgStarsTextField, avgStarsCheckBox);

        ArrayList<JTextField> inputsArray = new ArrayList<>();
        inputsArray.add(userNameTextField);
        inputsArray.add(revCountTextField);
        inputsArray.add(avgStarsTextField);

        // Glue
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        functionPanel.add(Box.createVerticalGlue(), gbc);

        // Button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> checkEntryValidity(inputsArray));
        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);
        searchButton.setPreferredSize(new Dimension(75, 25));
        searchButton.setBorder(new LineBorder(Color.WHITE, 2));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        functionPanel.add(searchButton, gbc);

    }

    private void addRow(RoundedPanel panel, GridBagConstraints gbc, String label, JTextField textField, JCheckBox checkBox) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel jLabel = new JLabel(label);
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(checkBox, gbc);
        checkBox.setSelected(true);
    }

    private void checkEntryValidity(ArrayList<JTextField> inputsArray) { // Need to check validity of both review count (needs to be a positive int) and avg stars (double 1-5)
        Boolean reviewsUsed = false;
        Boolean avgStarsUsed = false;
        if(!inputsArray.get(1).getText().isEmpty()) {
            reviewsUsed = true;
        }
        if(!inputsArray.get(2).getText().isEmpty()) {
            avgStarsUsed = true;
        }
        if(reviewsUsed || avgStarsUsed) {
            if(reviewsUsed) {
                if(isInt(inputsArray.get(1).getText())) {
                    if(Integer.parseInt(inputsArray.get(1).getText()) < 0) {
                        // Error invalid integer
                        String errorMessage = "Error: you must enter a positive integer (eg. 35).";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    } else if(avgStarsUsed) {
                        if(isDouble(inputsArray.get(2).getText())) {
                            if(Double.parseDouble(inputsArray.get(2).getText()) < 1.00 || Double.parseDouble(inputsArray.get(2).getText()) > 5.00) {
                                // Error invalid double
                                String errorMessage = "Error: you must enter a decimal from 1 - 5 with maximum 2 decimal places (eg. 3.25).";
                                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        functionController.handleUserSearch(inputsArray);
                    }
                }
            } else if(avgStarsUsed) {
                if(isDouble(inputsArray.get(2).getText())) {
                    if(Double.parseDouble(inputsArray.get(2).getText()) < 1.00 || Double.parseDouble(inputsArray.get(2).getText()) > 5.00) {
                        // Error invalid double
                        String errorMessage = "Error: you must enter a decimal from 1 - 5 with maximum 2 decimal places (eg. 3.25).";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        functionController.handleUserSearch(inputsArray);
                    }
                }
            }
        } else {
            functionController.handleUserSearch(inputsArray);
        }
    }

    private void importBackground(String imgPath) {
        try{
            this.img = ImageIO.read(new File(imgPath));
        } catch (IOException e){
            throw new Error("Error");
        }
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
