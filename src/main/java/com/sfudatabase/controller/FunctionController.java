package main.java.com.sfudatabase.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.java.com.sfudatabase.model.ImagePanel;

public class FunctionController {
    
    Connection con;
    public static Boolean loggedIn = false;
    public static String userID;
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

    public void handleBusSearch(ArrayList<JTextField> inputsArray, AtomicReference<String> orderByOption) {
        String busName = "";
        String busCity = "";
        String minStars = "";
        String orderByOptionString = (String) orderByOption.get();
        ArrayList<String> parameters = new ArrayList<>();

        StringBuilder sSQL = new StringBuilder("SELECT business_id, name, address, city, stars FROM business WHERE 1=1");

        if(!inputsArray.get(0).getText().isEmpty()) {
            busName = "%" + inputsArray.get(0).getText() + "%";
            parameters.add(busName);
            sSQL.append(" AND name LIKE ?");
        }
        if(!inputsArray.get(1).getText().isEmpty()) {
            busCity = inputsArray.get(1).getText();
            parameters.add(busCity);
            sSQL.append(" AND city = ?");
        }
        if(!inputsArray.get(2).getText().isEmpty()) {
            minStars = inputsArray.get(2).getText();
            parameters.add(minStars);
            sSQL.append(" AND stars >= ?");
        }

        if(orderByOptionString == "Name") {
            orderByOptionString = "name";
        } else if(orderByOptionString == "City") {
            orderByOptionString = "city";
        } else if(orderByOptionString == "Minimum Stars") {
            orderByOptionString = "stars";
        }

        if(orderByOptionString == "stars") {
            sSQL.append(" ORDER BY " + orderByOptionString + " DESC");
        } else {
            sSQL.append(" ORDER BY " + orderByOptionString);
        }

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
                if(!resultSet.next()) {
                    String errorMessage = "Not Found: Sorry! Your search did not return any results. Change the search parameters and try again.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else { // Switch to the search results screen
                    while(resultSet.next()) { // Need to get this into UI
                        String busID = resultSet.getString("business_id");
                        String name = resultSet.getString("name");
                        String address = resultSet.getString("address");
                        String city = resultSet.getString("city");
                        double stars = resultSet.getDouble("stars");

                        System.out.println("ID: " + busID + ", Name: " + name + ", Address: " + address + ", City: " + city + ", Stars: " + stars);
                    }
                }
            }
        } catch(SQLException se) {
			se.printStackTrace();
		}
    }

    public void handleUserSearch(ArrayList<JTextField> inputsArray) {
        String userName = "";
        String revCount = "";
        String avgStars = "";
        ArrayList<String> parameters = new ArrayList<>();

        StringBuilder sSQL = new StringBuilder("SELECT user_id, name, review_count, useful, funny, cool, average_stars, yelping_since FROM user_yelp WHERE 1=1");

        if(!inputsArray.get(0).getText().isEmpty()) {
            userName = "%" + inputsArray.get(0).getText() + "%";
            parameters.add(userName);
            sSQL.append(" AND name LIKE ?");
        }
        if(!inputsArray.get(1).getText().isEmpty()) {
            revCount = inputsArray.get(1).getText();
            parameters.add(revCount);
            sSQL.append(" AND review_count >= ?");
        }
        if(!inputsArray.get(2).getText().isEmpty()) {
            avgStars = inputsArray.get(2).getText();
            parameters.add(avgStars);
            sSQL.append(" AND average_stars >= ?");
        }

        sSQL.append(" ORDER BY name");

        try(PreparedStatement preparedStatement = con.prepareStatement(sSQL.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof String) {
                    preparedStatement.setString(i + 1, (String) param);
                } else if (param instanceof Integer) {
                    preparedStatement.setInt(i + 1, (int) param);
                } else if (param instanceof Double) {
                    preparedStatement.setDouble(i + 1, (Double) param);
                }
            }

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(!resultSet.next()) {
                    String errorMessage = "Not Found: Sorry! Your search did not return any results. Change the search parameters and try again.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else { // Switch to the search results screen
                    while(resultSet.next()) { // Need to get this into UI
                        String userID = resultSet.getString("user_id");
                        String name = resultSet.getString("name");
                        int reviewCount = resultSet.getInt("review_count");
                        int useful = resultSet.getInt("useful");
                        int funny = resultSet.getInt("funny");
                        int cool = resultSet.getInt("cool");
                        double averageStars = resultSet.getDouble("average_stars");
                        Timestamp userDate = resultSet.getTimestamp("yelping_since");
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String userSignupDate = dateFormatter.format(userDate);

                        System.out.println("ID: " + userID + ", Name: " + name + ", Review Count: " + reviewCount + ", Useful: " + useful + ", Funny: " + funny + ", Cool: " + cool + ", Average Stars: " + averageStars + ", Signup Date: " + userSignupDate);
                    }
                }
            }
        } catch(SQLException se) {
			se.printStackTrace();
		}
    }

    public void handleAddFriend(JTextField desiredFriendIDTextField) {

        String fSQL = "SELECT * FROM user_yelp WHERE user_id = ?";

        try(PreparedStatement fPreparedStatement = con.prepareStatement(fSQL)) {
            fPreparedStatement.setString(1, desiredFriendIDTextField.getText());
            try(ResultSet resultSet = fPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    String sSQL = "INSERT INTO friendship (user_id, friend) VALUES (?, ?)";

                    try(PreparedStatement preparedStatement = con.prepareStatement(sSQL)) {
                        preparedStatement.setString(1, FunctionController.userID);
                        preparedStatement.setString(2, desiredFriendIDTextField.getText());

                        //int rowsAffected = preparedStatement.executeUpdate();

                        String successMessage = "Success! You just added a new friend!";
                        JOptionPane.showMessageDialog(null, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                        desiredFriendIDTextField.setText("");
                        panelController.showPanel("imagePanel");
                    } catch(SQLException se) {
                        se.printStackTrace();
                    }
                } else {
                    String errorMessage = "Error: The friend you tried to add does not exist. Input another user ID to try again. (reminder: userID length is 22)";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch(SQLException se2) {
                se2.printStackTrace();
            }

        } catch(SQLException se) {
			se.printStackTrace();
		}
    }

    public void handleAddReview(ArrayList<JTextField> inputsArray) {

        String rSQL = "SELECT * FROM business WHERE business_id = ?";

        try(PreparedStatement rPreparedStatement = con.prepareStatement(rSQL)) {
            //fPreparedStatement.setString(1, desiredFriendIDTextField.getText()); // use business ID instead
            try(ResultSet resultSet = rPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    String sSQL = "INSERT INTO review (user_id, business_id, stars, date) VALUES (?, ?, ?, ?)";

                    try(PreparedStatement preparedStatement = con.prepareStatement(sSQL)) {
                        // Parameter assignment
                        /*
                        preparedStatement.setString(1, FunctionController.userID);
                        preparedStatement.setString(2, desiredFriendIDTextField.getText());
                        */

                        //int rowsAffected = preparedStatement.executeUpdate();

                        String successMessage = "Success! You just added a new friend!";
                        JOptionPane.showMessageDialog(null, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                        desiredFriendIDTextField.setText("");
                        panelController.showPanel("imagePanel");
                    } catch(SQLException se) {
                        se.printStackTrace();
                    }
                } else {
                    String errorMessage = "Error: The friend you tried to add does not exist. Input another user ID to try again. (reminder: userID length is 22)";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch(SQLException se2) {
                se2.printStackTrace();
            }

        } catch(SQLException se) {
			se.printStackTrace();
		}
    }

}
