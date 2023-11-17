package main.java.com.sfudatabase.controller;

import java.sql.Connection;

public class ImagePanelController {
    
    Connection con;

    public ImagePanelController(Connection con) {
        this.con = con;
    }

    public void handleLoginClick() {
        System.out.println("Login clicked");
    }

    public void handleBusinessSearchClick() {
        System.out.println("Business Search clicked");
    }

    public void handleUserSearchClick() {
        System.out.println("User Search clicked");
    }

    public void handleAddFriendClick() {
        System.out.println("Add Friend clicked");
    }

    public void handleAddReviewClick() {
        System.out.println("Add Review clicked");
    }
}
