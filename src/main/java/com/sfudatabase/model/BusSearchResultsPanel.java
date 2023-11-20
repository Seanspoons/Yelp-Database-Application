package main.java.com.sfudatabase.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import main.java.com.sfudatabase.controller.FunctionController;
import main.java.com.sfudatabase.controller.PanelController;
import main.java.com.sfudatabase.database.BusinessData;

public class BusSearchResultsPanel extends FunctionPanel {

    private FunctionController functionController;
    private PanelController panelController;
    private BufferedImage img;
    private JTextField busIDDisplayField;
    private JTextField busNameDisplayField;
    private JTextField busAddressDisplayField;
    private JTextField busCityDisplayField;
    private JTextField busStarsDisplayField;
    private ArrayList<BusinessData> busResultList;

    public BusSearchResultsPanel(String imgPath, FunctionController functionController, PanelController panelController) {
        super(panelController, functionController);
        this.functionController = functionController;
        this.panelController = panelController;
        importBackground(imgPath);

        functionPanel.setPreferredSize(new Dimension(300,200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        busIDDisplayField = new JTextField(22);
        busNameDisplayField = new JTextField(22);
        busAddressDisplayField = new JTextField(22);
        busCityDisplayField = new JTextField(22);
        busStarsDisplayField = new JTextField(22);

        addRow(functionPanel, gbc, "Business ID:", busIDDisplayField);
        addRow(functionPanel, gbc, "Name:", busNameDisplayField);
        addRow(functionPanel, gbc, "Address:", busAddressDisplayField);
        addRow(functionPanel, gbc, "City:", busCityDisplayField);
        addRow(functionPanel, gbc, "Stars:", busStarsDisplayField);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {goBack();});
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 2));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.5;
        functionPanel.add(backButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        JPanel buttonHolder = new JPanel(new GridBagLayout());
        buttonHolder.setOpaque(true);
        functionPanel.add(buttonHolder, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 0, 0, 0);
        JButton reviewButton = new JButton("Review");
        reviewButton.addActionListener(e -> addReview());
        reviewButton.setBackground(Color.BLACK);
        reviewButton.setForeground(Color.WHITE);
        reviewButton.setBorder(new LineBorder(Color.WHITE, 2));
        buttonHolder.add(reviewButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.insets = new Insets(0, 0, 0, 0);
        JButton scrollLeftButton = new JButton("<");
        scrollLeftButton.addActionListener(e -> functionController.moveToPreviousBusRow(busResultList));
        scrollLeftButton.setBackground(Color.BLACK);
        scrollLeftButton.setForeground(Color.WHITE);
        scrollLeftButton.setBorder(new LineBorder(Color.WHITE, 2));
        buttonHolder.add(scrollLeftButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        JButton scrollRightButton = new JButton(">");
        scrollRightButton.addActionListener(e -> functionController.moveToNextBusRow(busResultList));
        scrollRightButton.setBackground(Color.BLACK);
        scrollRightButton.setForeground(Color.WHITE);
        scrollRightButton.setBorder(new LineBorder(Color.WHITE, 2));
        buttonHolder.add(scrollRightButton, gbc);
    }

    private void goBack() {
        functionController.busResultList.clear();
        functionController.busResultIndex = 0;
        panelController.showPanel("busSearchPanel");
    }

    private void addReview() {
        panelController.setUserID(FunctionController.userID);
        panelController.setBusinessID(busResultList.get(functionController.busResultIndex).getBusinessID());
        panelController.showPanelReview("addReviewPanel", true);
    }

    private void addRow(RoundedPanel panel, GridBagConstraints gbc, String label, JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel jLabel = new JLabel(label);
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        panel.add(textField, gbc);
    }

    public void setBusID(String busID) {
        busIDDisplayField.setText(busID);
    }

    public void setBusName(String name) {
        busNameDisplayField.setText(name);
    }

    public void setAddress(String address) {
        busAddressDisplayField.setText(address);
    }

    public void setCity(String city) {
        busCityDisplayField.setText(city);
    }

    public void setStars(String stars) {
        busStarsDisplayField.setText(stars);
    }

    public void setResultList(ArrayList<BusinessData> busResultList) {
        this.busResultList = busResultList;
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
