import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import controller.FunctionController;
import controller.ImagePanelController;
import controller.PanelController;
import model.AddFriendPanel;
import model.AddReviewPanel;
import model.BusSearchPanel;
import model.ImagePanel;
import model.LoginPanel;
import model.UserSearchPanel;

public class Main {

    private static Connection con;

    public static void main(String[] args) {
		
		String sUsername = "s_sgw6";
		String sPassphrase = "N4he46gMmHAga223";
		
		String connectionUrl = "jdbc:sqlserver://cypress.csil.sfu.ca;";	
		
		
		try {
			con = DriverManager.getConnection(connectionUrl, sUsername, sPassphrase);
		} catch(SQLException se) {
			se.printStackTrace();
			String errorMessage = "Error: Failed to connect to the yelp database..";
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
				return;
		}
	
		// UI
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Yelp Database Application");

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            double screenWidth = screenSize.width * 0.8;
            int screenWidthInt = (int) screenWidth;
            double screenHeight = screenWidth * 9 / 16;
            int screenHeightInt = (int) screenHeight;

			PanelController panelController = new PanelController();
			FunctionController functionController = new FunctionController(panelController, con);
			AddReviewPanel addReviewPanel = new AddReviewPanel("/logo-background.png", functionController, panelController);
            ImagePanelController imagePanelController = new ImagePanelController(panelController, addReviewPanel.getUserIDTextField());
			panelController.setAddReviewPanel(addReviewPanel);

			// Create UI Panels
            ImagePanel imagePanel = new ImagePanel("/logo-background.png", imagePanelController);
			LoginPanel loginPanel = new LoginPanel("/logo-background.png", functionController, panelController);
			BusSearchPanel busSearchPanel = new BusSearchPanel("/logo-background.png", functionController, panelController);
			UserSearchPanel userSearchPanel = new UserSearchPanel("/logo-background.png", functionController, panelController);
			AddFriendPanel addFriendPanel = new AddFriendPanel("/logo-background.png", functionController, panelController);
			panelController.setAddFriendPanel(addFriendPanel);

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

