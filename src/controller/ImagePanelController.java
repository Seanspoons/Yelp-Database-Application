package controller;

import javax.swing.JTextField;

public class ImagePanelController {

    private PanelController panelController;
    private JTextField userIDTextField;

    public ImagePanelController(PanelController panelController, JTextField userIDTextField) {
        this.panelController = panelController;
        this.userIDTextField = userIDTextField;
    }

    public void handleLoginClick() {
        panelController.showPanel("loginPanel");
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
        panelController.showPanelReview("addReviewPanel", false); 
    }
}
