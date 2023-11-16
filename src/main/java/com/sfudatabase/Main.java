package main.java.com.sfudatabase;

import javax.swing.*;
import main.java.com.sfudatabase.controller.*;
import main.java.com.sfudatabase.model.*;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Yelp Database Application");

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            double screenWidth = screenSize.width * 0.8;
            int screenWidthInt = (int) screenWidth;
            double screenHeight = screenWidth * 9 / 16;
            int screenHeightInt = (int) screenHeight;

            ImagePanelController imagePanelController = new ImagePanelController();
            ImagePanel imagePanel = new ImagePanel("src/main/resources/img/logo-background.png", imagePanelController);

            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(screenWidthInt, screenHeightInt);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setContentPane(imagePanel);
            mainFrame.setVisible(true);
        });
    }
}

