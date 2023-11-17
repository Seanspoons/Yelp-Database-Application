package main.java.com.sfudatabase.controller;

import javax.swing.*;
import java.awt.*;

public class PanelController extends JPanel {
    private CardLayout cardLayout;
    public PanelController(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);
    }

    public void showPanel(String panelName){
        cardLayout.show(this, panelName);
    }
}
