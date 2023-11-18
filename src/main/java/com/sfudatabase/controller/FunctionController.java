package main.java.com.sfudatabase.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.java.com.sfudatabase.model.ImagePanel;

public class FunctionController {
    
    Connection con;
    public static Boolean loggedIn = false;
    String userID;
    PanelController panelController;

    public FunctionController(PanelController panelController, Connection con) {
        this.con = con;
        this.panelController = panelController;
    }

    public void handleLoginSubmit(String inputID) {
        String sSQL = "SELECT COUNT(*) FROM user_yelp WHERE user_id = ?";

        try(PreparedStatement preparedStatement = con.prepareStatement(sSQL)) {
            preparedStatement.setString(1, inputID);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if(count > 0) {
                        // Log on successful
                        userID = inputID;
                        loggedIn = true;
                        ImagePanel.loginItem.setEnabled(false);
                        ImagePanel.businessSearchItem.setEnabled(true);
                        ImagePanel.userSearchItem.setEnabled(true);
                        ImagePanel.addFriendItem.setEnabled(true);
                        ImagePanel.addReviewItem.setEnabled(true);
                        panelController.showPanel("imagePanel");
                    } else {
                        // Log on failed
                        String errorMessage = "Error: user_id is invalid. Please input a valid user_id.";
                        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch(SQLException se) {
			se.printStackTrace();
		}
    }

}
