package main.java.com.sfudatabase.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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

    public void handleBusSearch(ArrayList<JTextField> inputsArray) {
        String busName = "";
        String busCity = "";
        String minStars = "";
        Boolean minStarsUsed = false;
        ArrayList<String> parameters = new ArrayList<>();

        StringBuilder sSQL = new StringBuilder("SELECT business_id, name, address, city, stars FROM business WHERE 1=1");

        if(!inputsArray.get(0).getText().isEmpty()) {
            busName = inputsArray.get(0).getText();
            parameters.add(busName);
            sSQL.append(" AND name = ?");
        }
        if(!inputsArray.get(1).getText().isEmpty()) {
            busCity = inputsArray.get(1).getText();
            parameters.add(busCity);
            sSQL.append(" AND city = ?");
        }
        if(inputsArray.get(2).getText() != "") {
            if(isDouble(inputsArray.get(2).getText())) {
                minStars = inputsArray.get(2).getText();
                minStarsUsed = true;
                parameters.add(minStars);
                sSQL.append(" AND stars >= ?");
            } else {
            }
        }

        System.out.println(sSQL.toString());

        try(PreparedStatement preparedStatement = con.prepareStatement(sSQL.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof String) {
                    preparedStatement.setString(i + 1, (String) param);
                } else if (param instanceof Double) {
                    preparedStatement.setDouble(i + 1, (Double) param);
                }
            }

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) { // Need to get this into UI
                    String busID = resultSet.getString("business_id");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    String city = resultSet.getString("city");
                    double stars = resultSet.getDouble("stars");

                    System.out.println("ID: " + busID + ", Name: " + name + ", Address: " + address + ", City: " + city + ", Stars: " + stars);
                }
            }
        } catch(SQLException se) {
			se.printStackTrace();
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

}
