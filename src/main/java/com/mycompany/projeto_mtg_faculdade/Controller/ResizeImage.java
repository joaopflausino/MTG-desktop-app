/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;  
import javax.imageio.ImageIO;
/**
 *
 * @author Jomes
 */
public class ResizeImage {

    /**
     * @param args the command line arguments
     */
    private double targetWidth;
    private double targetHeight;

    public void SetTargetWidth(double targetWidth, double targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
    }

    public ImageIcon resizeImageFromFile(String filePath) throws IOException {
        ImageIcon resizedIcon = null;
        Image inImage = ImageIO.read(new File("src/main/resources" + filePath));


        // Check if URL is null
        if (inImage == null) {
            System.err.println("Resource not found: " + filePath);
            return null;
        }
        // Check if resource is found
        

        ImageIcon myimg = new ImageIcon(inImage);
        SetTargetWidth(myimg.getIconWidth() * 0.25, myimg.getIconHeight() * 0.25);
        Image resizedImage = myimg.getImage().getScaledInstance((int) targetWidth, (int) targetHeight, Image.SCALE_DEFAULT);
        resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }

}