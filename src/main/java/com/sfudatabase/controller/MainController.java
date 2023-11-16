package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainController {
    
    @FXML
    private MenuItem loginItem;
    @FXML
    private MenuItem businessSearchItem;
    @FXML
    private MenuItem userSearchItem;
    @FXML
    private MenuItem addFriendItem;
    @FXML
    private MenuItem addReviewItem;

    public void initialize() {
        loginItem.setOnAction(this::handleLoginClick);
        businessSearchItem.setOnAction(this::handleBusinessSearchClick);
        userSearchItem.setOnAction(this::handleUserSearchClick);
        addFriendItem.setOnAction(this::handleAddFriendClick);
        addReviewItem.setOnAction(this::handleAddReviewClick);
    }

    private void handleLoginClick(ActionEvent event) {
        System.out.println("Login clicked");
    }

    private void handleBusinessSearchClick(ActionEvent event) {
        System.out.println("Business Search clicked");
    }

    private void handleUserSearchClick(ActionEvent event) {
        System.out.println("User Search clicked");
    }

    private void handleAddFriendClick(ActionEvent event) {
        System.out.println("Add Friend clicked");
    }

    private void handleAddReviewClick(ActionEvent event) {
        System.out.println("Add Review clicked");
    }

}
