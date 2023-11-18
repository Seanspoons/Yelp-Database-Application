package main.java.com.sfudatabase;

import javax.swing.*;
import main.java.com.sfudatabase.controller.*;
import main.java.com.sfudatabase.model.*;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static Connection con;
	private static String space = "                                           ";

    public static void main(String[] args) {

        // Database connection
        PreparedStatement pstmt = null;
		ResultSet rs;
		String sSQL= "select * from helpdesk";	//the table was created by helpdesk
		String temp="";
		
		String sUsername = "s_sgw6";
		String sPassphrase = "N4he46gMmHAga223";
		
        //String connectionUrl = "jdbc:sqlserver://cypress.csil.sfu.ca;" + "user = " + sUsername + ";" + "password =" + sPassphrase;
		String connectionUrl = "jdbc:sqlserver://cypress.csil.sfu.ca;";	
		
		/*
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch(ClassNotFoundException ce) {
			System.out.println("\n\nNo JDBC Driver for SQL Server; exit now.\n\n");
			return;
		}
		*/
		
		try {
			//con = DriverManager.getConnection(connectionUrl);
			con = DriverManager.getConnection(connectionUrl, sUsername, sPassphrase); // this doesn't work either
			System.out.println("\n\nConnection established\n\n");
		} catch(SQLException se) {
			System.out.println ( "\n\nFailed to connect to CSIL SQL Server; exit now.\n\n" );
				return;
		}
		
		/* Test statement to see if connection worked
		try {
			pstmt = con.prepareStatement(sSQL);
			rs = pstmt.executeQuery();
			
			System.out.println("\nThe table 'helpdesk' contains:\n\n");
			
			while (rs.next()) {
				temp= rs.getString("username");	//the table has a field 'username'
				System.out.println(temp);
			}
			rs.close();
			System.out.println("\nSuccessfully connected to CSIL SQL Server!\n\n");
		} catch(SQLException se) {
			System.out.println("\nSQL Exception occurred, the state : "+ se.getSQLState()+"\nMessage:\n"+se.getMessage()+"\n");
			return;
		}
		*/

		// UI
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Yelp Database Application");

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double screenWidth = screenSize.width * 0.8;
            int screenWidthInt = (int) screenWidth;
            double screenHeight = screenWidth * 9 / 16;
            int screenHeightInt = (int) screenHeight;

			PanelController panelController = new PanelController();
            ImagePanelController imagePanelController = new ImagePanelController(panelController);
			FunctionController functionController = new FunctionController(panelController, con);

			// Create UI Panels
            ImagePanel imagePanel = new ImagePanel("src/main/resources/img/logo-background.png", imagePanelController);
			LoginPanel loginPanel = new LoginPanel("src/main/resources/img/logo-background.png", functionController);
			BusSearchPanel busSearchPanel = new BusSearchPanel("src/main/resources/img/logo-background.png", functionController);
			UserSearchPanel userSearchPanel = new UserSearchPanel("src/main/resources/img/logo-background.png", functionController);
			AddFriendPanel addFriendPanel = new AddFriendPanel("src/main/resources/img/logo-background.png", functionController);
			AddReviewPanel addReviewPanel = new AddReviewPanel("src/main/resources/img/logo-background.png", functionController);

			panelController.add(imagePanel, "imagePanel");
			panelController.add(loginPanel, "loginPanel");
			panelController.add(busSearchPanel, "busSearchPanel");
			panelController.add(userSearchPanel, "userSearchPanel");
			panelController.add(addFriendPanel, "addFriendPanel");
			panelController.add(addReviewPanel, "addReviewPanel");
			panelController.showPanel("imagePanel");

			// Set behaviour of the main JFrame
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(screenWidthInt, screenHeightInt);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setContentPane(panelController);
            mainFrame.setVisible(true);
        });
    }
}

