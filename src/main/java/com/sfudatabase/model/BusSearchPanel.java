package main.java.com.sfudatabase.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.java.com.sfudatabase.controller.FunctionController;
import main.java.com.sfudatabase.controller.PanelController;

public class BusSearchPanel extends FunctionPanel {

    FunctionController functionController;
    PanelController panelController;
    BufferedImage img;

    public BusSearchPanel(String imgPath, FunctionController functionController, PanelController panelController) {
        super(panelController);
        this.functionController = functionController;
        this.panelController = panelController;
        importBackground(imgPath);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField busNameTextField = new JTextField(15);
        JTextField busCityTextField = new JTextField(15);
        JTextField minStarsTextField = new JTextField(15);

        JCheckBox busNameCheckBox = new JCheckBox();
        JCheckBox busCityCheckBox = new JCheckBox();
        JCheckBox minStarsCheckBox = new JCheckBox();

        busNameCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(busNameCheckBox.isSelected()) {
                    busNameTextField.setEnabled(true);
                } else {
                    busNameTextField.setEnabled(false);
                    busNameTextField.setText("");
                }
            }
        });
        busCityCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(busCityCheckBox.isSelected()) {
                    busCityTextField.setEnabled(true);
                } else {
                    busCityTextField.setEnabled(false);
                    busCityTextField.setText("");
                }
            }
        });
        minStarsCheckBox.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(minStarsCheckBox.isSelected()) {
                    minStarsTextField.setEnabled(true);
                } else {
                    minStarsTextField.setEnabled(false);
                    minStarsTextField.setText("");
                }
            }
        });

        addRow(functionPanel, gbc, "Business Name:", busNameTextField, busNameCheckBox);
        addRow(functionPanel, gbc, "Business City:", busCityTextField, busCityCheckBox);
        addRow(functionPanel, gbc, "Minimum Stars:", minStarsTextField, minStarsCheckBox);

        ArrayList<JTextField> inputsArray = new ArrayList<>();
        inputsArray.add(busNameTextField);
        inputsArray.add(busCityTextField);
        inputsArray.add(minStarsTextField);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel jLabel = new JLabel("Order By: ");
        functionPanel.add(jLabel, gbc);

        // Order by option
        String[] options = {"Name", "City", "Minimum Stars"};
        JComboBox<String> orderByComboBox = new JComboBox<>(options);
        AtomicReference<String> orderByOption = new AtomicReference<>("Name");
        orderByComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    orderByOption.set((String) orderByComboBox.getSelectedItem());
                }
            }
        });
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        functionPanel.add(orderByComboBox, gbc);

        // Button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> checkEntryValidity(inputsArray, orderByOption));
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

    private void checkEntryValidity(ArrayList<JTextField> inputsArray, AtomicReference<String> orderByOption) {
        if(!inputsArray.get(2).getText().isEmpty()) {
            if(isDouble(inputsArray.get(2).getText())) {
                if(Double.parseDouble(inputsArray.get(2).getText()) < 1.0 || Double.parseDouble(inputsArray.get(2).getText()) > 5.0) {
                    // Error invalid double
                    String errorMessage = "Error: you must enter a decimal from 1 - 5 with maximum 1 decimal place (eg. 3.2).";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    functionController.handleBusSearch(inputsArray, orderByOption);
                }
            } else {
                // Error not double
                String errorMessage = "Error: you must enter a decimal from 1 - 5 with maximum 1 decimal place (eg. 3.2).";
                JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            functionController.handleBusSearch(inputsArray, orderByOption);
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

    @Override
    protected  void paintComponent(Graphics g){
        super.paintComponent(g);
        if (img != null){
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
}
