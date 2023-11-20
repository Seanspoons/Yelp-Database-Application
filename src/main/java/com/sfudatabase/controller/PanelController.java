package main.java.com.sfudatabase.controller;

import java.awt.CardLayout;

import javax.swing.JPanel;

import main.java.com.sfudatabase.model.AddFriendPanel;
import main.java.com.sfudatabase.model.AddReviewPanel;

public class PanelController extends JPanel {
    private CardLayout cardLayout;
    private String currentPanel;
    private String userID;
    private String friendID;
    private String businessID;
    private AddReviewPanel addReviewPanel;
    private AddFriendPanel addFriendPanel;

    public PanelController(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);
    }

    public void showPanel(String panelName){
        cardLayout.show(this, panelName);
        currentPanel = panelName;
    }

    public void showPanelReview(String panelName, Boolean wasSearching){
        addReviewPanel.setWasSearching(wasSearching);
        if(!wasSearching) {
            addReviewPanel.setWasSearching(wasSearching);
            cardLayout.show(this, panelName);
            currentPanel = panelName;
        } else {
            addReviewPanel.setUserIDTextField(userID);
            addReviewPanel.setBusIDTextField(businessID);
            addReviewPanel.setWasSearching(true);
            cardLayout.show(this, panelName);
            currentPanel = panelName;
        }
    }

    public void showPanelFriend(String panelName, Boolean wasSearching){
        addFriendPanel.setWasSearching(wasSearching);
        if(!wasSearching) {
            addFriendPanel.setWasSearching(wasSearching);
            cardLayout.show(this, panelName);
            currentPanel = panelName;
        } else {
            addFriendPanel.setAddFriendTextField(friendID);
            addFriendPanel.setWasSearching(true);
            cardLayout.show(this, panelName);
            currentPanel = panelName;
        }
    }

    public String getCurrentPanelName() {
        return currentPanel;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public void setAddReviewPanel(AddReviewPanel addReviewPanel) {
        this.addReviewPanel = addReviewPanel;
    }

    public void setAddFriendPanel(AddFriendPanel addFriendPanel) {
        this.addFriendPanel = addFriendPanel;
    }

    public String getUserID() {
        return userID;
    }

    public String getBusinessID() {
        return businessID;
    }
}
