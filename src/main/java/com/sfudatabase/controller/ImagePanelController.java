package main.java.com.sfudatabase.controller;

import javax.swing.JTextField;

public class ImagePanelController { // Just controls the UI

    PanelController panelController;
    JTextField userIDTextField;

    public ImagePanelController(PanelController panelController, JTextField userIDTextField) {
        this.panelController = panelController;
        this.userIDTextField = userIDTextField;
    }

    public void handleLoginClick() {

        if(FunctionController.loggedIn) { // First check if logged in
            // If logged in - tell user they are already logged in
        } else {
            // Else not logged in - open login ui
            panelController.showPanel("loginPanel");
        }
    }

    public void handleBusinessSearchClick() {
        panelController.showPanel("busSearchPanel");    
    }

    public void handleUserSearchClick() {
        panelController.showPanel("userSearchPanel"); 
    }

    public void handleAddFriendClick() {
        panelController.showPanel("addFriendPanel"); 
    }

    public void handleAddReviewClick() {
        userIDTextField.setText(FunctionController.userID);
        userIDTextField.setEnabled(false);
        panelController.showPanel("addReviewPanel"); 
    }
}
