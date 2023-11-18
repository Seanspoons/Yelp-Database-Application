package main.java.com.sfudatabase.model;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import main.java.com.sfudatabase.controller.FunctionController;

public class BusSearchPanel extends FunctionPanel {

    FunctionController functionController;
    BufferedImage img;

    public BusSearchPanel(String imgPath, FunctionController functionController) {
        this.functionController = functionController;
        importBackground(imgPath);
    }

    private void importBackground(String imgPath) {
        try{
            this.img = ImageIO.read(new File(imgPath));
        } catch (IOException e){
            throw new Error("Error");
        }
    }

    @Override
    protected  void paintComponent(Graphics g){
        super.paintComponent(g);
        if (img != null){
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
}
