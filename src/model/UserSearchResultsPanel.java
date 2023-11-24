package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.FunctionController;
import controller.PanelController;
import database.UserData;

public class UserSearchResultsPanel extends FunctionPanel {

    private FunctionController functionController;
    private PanelController panelController;
    private BufferedImage img;
    private JTextField userIDDisplayField;
    private JTextField userNameDisplayField;
    private JTextField userReviewCountDisplayField;
    private JTextField userUsefulDisplayField;
    private JTextField userFunnyDisplayField;
    private JTextField userCoolDisplayField;
    private JTextField userAverageStarsDisplayField;
    private JTextField userSignupDateDisplayField;
    private ArrayList<UserData> userResultList;

    public UserSearchResultsPanel(String imgPath, FunctionController functionController, PanelController panelController) {
        super(panelController, functionController);
        this.functionController = functionController;
        this.panelController = panelController;
        importBackground(imgPath);

        functionPanel.setPreferredSize(new Dimension(350,270));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        userIDDisplayField = new JTextField(22);
        userNameDisplayField = new JTextField(22);
        userReviewCountDisplayField = new JTextField(22);
        userUsefulDisplayField = new JTextField(22);
        userFunnyDisplayField = new JTextField(22);
        userCoolDisplayField = new JTextField(22);
        userAverageStarsDisplayField = new JTextField(22);
        userSignupDateDisplayField = new JTextField(22);

        addRow(functionPanel, gbc, "User ID:", userIDDisplayField);
        addRow(functionPanel, gbc, "Name:", userNameDisplayField);
        addRow(functionPanel, gbc, "Review Count:", userReviewCountDisplayField);
        addRow(functionPanel, gbc, "Useful:", userUsefulDisplayField);
        addRow(functionPanel, gbc, "Funny:", userFunnyDisplayField);
        addRow(functionPanel, gbc, "Cool:", userCoolDisplayField);
        addRow(functionPanel, gbc, "Average Stars:", userAverageStarsDisplayField);
        addRow(functionPanel, gbc, "Signup Date:", userSignupDateDisplayField);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {goBack();});
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(new LineBorder(Color.WHITE, 2));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0.5;
        functionPanel.add(backButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.weightx = 0.0;
        JPanel buttonHolder = new JPanel(new GridBagLayout());
        buttonHolder.setOpaque(true);
        functionPanel.add(buttonHolder, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.insets = new Insets(0, 0, 0, 0);
        JButton reviewButton = new JButton("Add Friend");
        reviewButton.addActionListener(e -> addFriend());
        reviewButton.setBackground(Color.BLACK);
        reviewButton.setForeground(Color.WHITE);
        reviewButton.setBorder(new LineBorder(Color.WHITE, 2));
        buttonHolder.add(reviewButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.insets = new Insets(0, 0, 0, 0);
        JButton scrollLeftButton = new JButton("<");
        scrollLeftButton.addActionListener(e -> functionController.moveToPreviousUserRow(userResultList));
        scrollLeftButton.setBackground(Color.BLACK);
        scrollLeftButton.setForeground(Color.WHITE);
        scrollLeftButton.setBorder(new LineBorder(Color.WHITE, 2));
        buttonHolder.add(scrollLeftButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        JButton scrollRightButton = new JButton(">");
        scrollRightButton.addActionListener(e -> functionController.moveToNextUserRow(userResultList));
        scrollRightButton.setBackground(Color.BLACK);
        scrollRightButton.setForeground(Color.WHITE);
        scrollRightButton.setBorder(new LineBorder(Color.WHITE, 2));
        buttonHolder.add(scrollRightButton, gbc);
    }

    private void goBack() {
        functionController.userResultList.clear();
        functionController.userResultIndex = 0;
        panelController.showPanel("userSearchPanel");
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

    public void setResultList(ArrayList<UserData> userResultList) {
        this.userResultList = userResultList;
    }

    private void addFriend() {
        panelController.setFriendID(userResultList.get(functionController.userResultIndex).getUserID());
        panelController.showPanelFriend("addFriendPanel", true);
    }

    public void setUserID(String userID) {
        userIDDisplayField.setText(userID);
    }

    public void setName(String name) {
        userNameDisplayField.setText(name);
    }

    public void setReviewCount(String reviewCount) {
        userReviewCountDisplayField.setText(reviewCount);
    }

    public void setUseful(String useful) {
        userUsefulDisplayField.setText(useful);
    }

    public void setFunny(String funny) {
        userFunnyDisplayField.setText(funny);
    }

    public void setCool(String cool) {
        userCoolDisplayField.setText(cool);
    }

    public void setAverageStars(String averageStars) {
        userAverageStarsDisplayField.setText(averageStars);
    }

    public void setSignupDate(String signupDate) {
        userSignupDateDisplayField.setText(signupDate);
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
    		System.out.println("Error stream is null in UserSearchResultsPanel");
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
