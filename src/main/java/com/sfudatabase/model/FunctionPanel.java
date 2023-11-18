package main.java.com.sfudatabase.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.JPanel;

public class FunctionPanel extends JPanel {

    RoundedPanel functionPanel;

    public FunctionPanel() {

        // Add vertical glue above
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 2.0;
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

        // Add vertical glue below
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        this.add(Box.createVerticalGlue(), gbc);

    }
    
}
