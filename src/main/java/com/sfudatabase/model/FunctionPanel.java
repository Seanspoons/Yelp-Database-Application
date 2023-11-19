package main.java.com.sfudatabase.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import main.java.com.sfudatabase.controller.PanelController;

public class FunctionPanel extends JPanel {

    RoundedPanel functionPanel;
    PanelController panelController;

    public FunctionPanel(PanelController panelController) {
        this.panelController = panelController;

        // Add vertical glue above
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 2.5;
        this.add(Box.createVerticalGlue(), gbc);

        // Set up constraints for login panel
        functionPanel = new RoundedPanel(20);
        functionPanel.setLayout(new GridBagLayout());
        functionPanel.setOpaque(false);
        functionPanel.setPreferredSize(new Dimension(300,175));
        functionPanel.setBackground(new Color(255,255,255, (int) (255* 0.75)));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(functionPanel, gbc);

        // Add Menu button
        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(e -> panelController.showPanel("imagePanel"));
        menuButton.setBackground(Color.BLACK);
        menuButton.setForeground(Color.WHITE);
        menuButton.setPreferredSize(new Dimension(75, 25));
        menuButton.setBorder(new LineBorder(Color.WHITE, 2));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(menuButton, gbc);

        // Add vertical glue below
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        this.add(Box.createVerticalGlue(), gbc);

    }
    
}
