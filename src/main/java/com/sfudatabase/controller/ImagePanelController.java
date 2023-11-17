package main.java.com.sfudatabase.controller;

public class ImagePanelController { // Just controls the UI

    PanelController panelController;

    public ImagePanelController(PanelController panelController) {
        this.panelController = panelController;
    }

    public void handleLoginClick() {
        System.out.println("Login clicked");

        if(FunctionController.loggedIn) { // First check if logged in
            // If logged in - tell user they are already logged in
        } else {
            // Else not logged in - open login ui
            panelController.showPanel("loginPanel");
        }
    }

    public void handleBusinessSearchClick() {
        System.out.println("Business Search clicked");

        if(FunctionController.loggedIn) { // First check if logged in
            // If logged in - open business search ui
        } else {
            // Else not logged in - tell user they need to be logged in to do this
        }
    }

    public void handleUserSearchClick() {
        System.out.println("User Search clicked");

        if(FunctionController.loggedIn) { // First check if logged in
            // If logged in - open user search ui
        } else {
            // Else not logged in - tell user they need to be logged in to do this
        }
    }

    public void handleAddFriendClick() {
        System.out.println("Add Friend clicked");

        if(FunctionController.loggedIn) { // First check if logged in
            // If logged in - open add friend ui
        } else {
            // Else not logged in - tell user they need to be logged in to do this
        }
    }

    public void handleAddReviewClick() {
        System.out.println("Add Review clicked");

        if(FunctionController.loggedIn) { // First check if logged in
            // If logged in - open add friend ui
        } else {
            // Else not logged in - tell user they need to be logged in to do this
        }
    }
}
