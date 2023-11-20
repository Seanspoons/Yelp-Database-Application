package main.java.com.sfudatabase.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
    
    private int cornerRadius;

    public RoundedPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);

        g2d.setColor(getBackground());
        g2d.fill(roundedRectangle);

        g2d.dispose();
    }
}
