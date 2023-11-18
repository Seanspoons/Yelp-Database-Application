package main.java.com.sfudatabase.controller;

public class ImagePanelController { // Just controls the UI

    PanelController panelController;

    public ImagePanelController(PanelController panelController) {
        this.panelController = panelController;
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
        panelController.showPanel("addReviewPanel"); 
    }
}
