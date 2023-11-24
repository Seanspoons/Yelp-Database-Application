package controller;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import database.BusinessData;
import database.UserData;
import model.BusSearchResultsPanel;
import model.ImagePanel;
import model.UserSearchResultsPanel;

public class FunctionController {
    
    private Connection con;
    public static Boolean loggedIn = false;
    public static String userID;
    private PanelController panelController;
    private BusSearchResultsPanel busSearchResultsPanel;
    private UserSearchResultsPanel userSearchResultsPanel;
    public ArrayList<BusinessData> busResultList;
    public int busResultIndex;
    public ArrayList<UserData> userResultList;
    public int userResultIndex;

    public FunctionController(PanelController panelController, Connection con) {
        this.con = con;
        this.panelController = panelController;
        busResultList = new ArrayList<>();
        userResultList = new ArrayList<>();
        busResultIndex = 0;
        userResultIndex = 0;

        busSearchResultsPanel = new BusSearchResultsPanel("/logo-background.png", this, panelController);
        userSearchResultsPanel = new UserSearchResultsPanel("/logo-background.png", this, panelController);
        panelController.add(busSearchResultsPanel, "busSearchResultsPanel");
        panelController.add(userSearchResultsPanel, "userSearchResultsPanel");
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
        busResultIndex = 0;
        busResultList.clear();
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

        try(PreparedStatement preparedStatement = con.prepareStatement(sSQL.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof String) {
                    preparedStatement.setString(i + 1, (String) param);
                } else if (param instanceof Double) {
                    preparedStatement.setDouble(i + 1, (Double) param);
                }
            }

            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                Boolean hasResults = resultSet.next();
                if(!hasResults) {
                    String errorMessage = "Not Found: Sorry! Your search did not return any results. Change the search parameters and try again.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    panelController.showPanel("busSearchResultsPanel");
                    busSearchResultsPanel.setResultList(busResultList);
                    do {
                        String businessID = resultSet.getString("business_id");
                        String name = resultSet.getString("name");
                        String address = resultSet.getString("address");
                        String city = resultSet.getString("city");
                        double stars = resultSet.getDouble("stars");
                        String starsString = String.valueOf(stars);

                        BusinessData businessData = new BusinessData(businessID, name, address, city, starsString);
                        busResultList.add(businessData);

                    } while(resultSet.next());
                    displayCurrentBusRow(busResultList);
                }
            }
        } catch(SQLException se) {
			se.printStackTrace();
		}
    }

    private void displayCurrentBusRow(ArrayList<BusinessData> busResultList) {
        busSearchResultsPanel.setBusID(busResultList.get(busResultIndex).getBusinessID());
        busSearchResultsPanel.setBusName(busResultList.get(busResultIndex).getName());
        busSearchResultsPanel.setAddress(busResultList.get(busResultIndex).getAddress());
        busSearchResultsPanel.setCity(busResultList.get(busResultIndex).getCity());
        busSearchResultsPanel.setStars(busResultList.get(busResultIndex).getStars());
    }

    private void displayCurrentUserRow(ArrayList<UserData> userResultList) {
        userSearchResultsPanel.setUserID(userResultList.get(userResultIndex).getUserID());
        userSearchResultsPanel.setName(userResultList.get(userResultIndex).getName());
        userSearchResultsPanel.setReviewCount(userResultList.get(userResultIndex).getReviewCount());
        userSearchResultsPanel.setUseful(userResultList.get(userResultIndex).getUseful());
        userSearchResultsPanel.setFunny(userResultList.get(userResultIndex).getFunny());
        userSearchResultsPanel.setCool(userResultList.get(userResultIndex).getCool());
        userSearchResultsPanel.setAverageStars(userResultList.get(userResultIndex).getAverageStars());
        userSearchResultsPanel.setSignupDate(userResultList.get(userResultIndex).getSignupDate());
    }

    public void moveToNextBusRow(ArrayList<BusinessData> busResultList) {
        if(busResultIndex != busResultList.size()-1) {
            busResultIndex++;
            displayCurrentBusRow(busResultList);
        } else {
            String infoMessage = "You have reached the end of the search results. Continue looking by scrolling backwards or search again.";
            JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void moveToNextUserRow(ArrayList<UserData> userResultList) {
        if(userResultIndex != userResultList.size()-1) {
            userResultIndex++;
            displayCurrentUserRow(userResultList);
        } else {
            String infoMessage = "You have reached the end of the search results. Continue looking by scrolling backwards or search again.";
            JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void moveToPreviousBusRow(ArrayList<BusinessData> busResultList) {
        if(busResultIndex != 0) {
            busResultIndex--;
            displayCurrentBusRow(busResultList);
        } else {
            String infoMessage = "You have reached the beginning of the search results. Continue looking by scrolling forwards or search again.";
            JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void moveToPreviousUserRow(ArrayList<UserData> userResultList) {
        if(userResultIndex != 0) {
            userResultIndex--;
            displayCurrentUserRow(userResultList);
        } else {
            String infoMessage = "You have reached the beginning of the search results. Continue looking by scrolling forwards or search again.";
            JOptionPane.showMessageDialog(null, infoMessage, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void handleUserSearch(ArrayList<JTextField> inputsArray) {
        userResultIndex = 0;
        userResultList.clear();
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
                Boolean hasResults = resultSet.next();
                if(!hasResults) {
                    String errorMessage = "Not Found: Sorry! Your search did not return any results. Change the search parameters and try again.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    panelController.showPanel("userSearchResultsPanel");
                    userSearchResultsPanel.setResultList(userResultList);
                    do {
                        String userID = resultSet.getString("user_id");
                        String name = resultSet.getString("name");
                        int reviewCount = resultSet.getInt("review_count");
                        int useful = resultSet.getInt("useful");
                        int funny = resultSet.getInt("funny");
                        int cool = resultSet.getInt("cool");
                        double averageStars = resultSet.getDouble("average_stars");
                        Timestamp userDate = resultSet.getTimestamp("yelping_since");
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        String reviewCountString = String.valueOf(reviewCount);
                        String usefulString = String.valueOf(useful);
                        String funnyString = String.valueOf(funny);
                        String coolString = String.valueOf(cool);
                        String averageStarString = String.valueOf(averageStars);
                        String userSignupDate = dateFormatter.format(userDate);

                        UserData userData = new UserData(userID, name, reviewCountString, usefulString, funnyString, coolString, averageStarString, userSignupDate);
                        userResultList.add(userData);
                    } while(resultSet.next());
                    displayCurrentUserRow(userResultList);
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
                	String fSQL2 = "SELECT * FROM friendship WHERE user_id = ? AND friend = ?";
                	
                	try(PreparedStatement fPreparedStatement2 = con.prepareStatement(fSQL2)) {
                		fPreparedStatement2.setString(1, FunctionController.userID);
                		fPreparedStatement2.setString(2, desiredFriendIDTextField.getText());
                		
                		try(ResultSet fResultSet = fPreparedStatement2.executeQuery()) {
                			Boolean hasResults = fResultSet.next();
                            if(!hasResults) {
                            	String sSQL = "INSERT INTO friendship (user_id, friend) VALUES (?, ?)";

                                try(PreparedStatement preparedStatement = con.prepareStatement(sSQL)) {
                                    preparedStatement.setString(1, FunctionController.userID);
                                    preparedStatement.setString(2, desiredFriendIDTextField.getText());

                                    int rowsAffected = preparedStatement.executeUpdate();
                                    System.out.println(rowsAffected);

                                    String successMessage = "Success! You just added a new friend!";
                                    JOptionPane.showMessageDialog(null, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                                    desiredFriendIDTextField.setText("");
                                    panelController.showPanel("imagePanel");
                                } catch(SQLException se) {
                                    se.printStackTrace();
                                }
                            } else {
                            	String errorMessage1 = "Error: You and the requested user are already friends. Input a different user and try again.";
                                JOptionPane.showMessageDialog(null, errorMessage1, "Error", JOptionPane.ERROR_MESSAGE);
                            }
                		}
                	} catch (SQLException se3) {
                		se3.printStackTrace();
                	}
                } else {
                    String errorMessage2 = "Error: The friend you tried to add does not exist. Input another user ID to try again. (reminder: userID length is 22)";
                    JOptionPane.showMessageDialog(null, errorMessage2, "Error", JOptionPane.ERROR_MESSAGE);
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
            rPreparedStatement.setString(1, inputsArray.get(1).getText());
            try(ResultSet resultSet = rPreparedStatement.executeQuery()) {
                if(resultSet.next()) {
                	
                	String rSQL2 = "SELECT * FROM review WHERE business_id = ? AND user_id = ?";
                	
                	try(PreparedStatement rPreparedStatement2 = con.prepareStatement(rSQL2)) {
                		rPreparedStatement2.setString(1, inputsArray.get(1).getText());
                		rPreparedStatement2.setString(2, FunctionController.userID);
                		try(ResultSet resultSet2 = rPreparedStatement2.executeQuery()) {
                			Boolean hasResults = resultSet2.next();
                			
                			if(!hasResults) {
                				String sSQL = "INSERT INTO review (review_id, user_id, business_id, stars, date) VALUES (?, ?, ?, ?, ?)";

                                try(PreparedStatement preparedStatement = con.prepareStatement(sSQL)) {
                                    String review_id = generateReviewID();
                                    preparedStatement.setString(1, review_id);
                                    preparedStatement.setString(2, inputsArray.get(0).getText());
                                    preparedStatement.setString(3, inputsArray.get(1).getText());
                                    preparedStatement.setInt(4, Integer.parseInt(inputsArray.get(2).getText()));
                                    Instant currentInstant = Instant.now();
                                    Timestamp currentTimestamp = Timestamp.from(currentInstant);
                                    preparedStatement.setTimestamp(5, currentTimestamp);

                                    int rowsAffected = preparedStatement.executeUpdate();
                                    System.out.println(rowsAffected);

                                    String successMessage = "Success! You just added a new review";
                                    JOptionPane.showMessageDialog(null, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                                    inputsArray.get(1).setText("");
                                    inputsArray.get(2).setText("");
                                    panelController.showPanel("imagePanel");
                                } catch(SQLException se) {
                                    se.printStackTrace();
                                }
                			} else {
                				String errorMessage1 = "Error: You have already reviewed the requested business. Input another business to try again.";
                                JOptionPane.showMessageDialog(null, errorMessage1, "Error", JOptionPane.ERROR_MESSAGE);
                			}
                		} catch (SQLException se3) {
                			se3.printStackTrace();
                		}
                		
                	} catch (SQLException se2) {
                		se2.printStackTrace();
                	}
                } else {
                    String errorMessage2 = "Error: The business you tried to review does not exist. (reminder: business ID length is 22)";
                    JOptionPane.showMessageDialog(null, errorMessage2, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch(SQLException se2) {
                se2.printStackTrace();
            }

        } catch(SQLException se) {
			se.printStackTrace();
		}
    }

    public String generateReviewID() {
        String availableCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

        StringBuilder reviewID = new StringBuilder(22);
        SecureRandom random = new SecureRandom();

        for(int i = 0; i < 22; i++) {
            int randomIndex = random.nextInt(availableCharacters.length());
            char randomChar = availableCharacters.charAt(randomIndex);
            reviewID.append(randomChar);
        }

        return reviewID.toString();
    }

}
