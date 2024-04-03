/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.net.URL;

/**
 *
 * @author joaox
 */
public class ImageTableCellRenderer  extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Call super method to get the default rendering
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Check if the value is a URL
        if (value instanceof URL) {
            // Load image from URL
            ImageIcon icon = new ImageIcon(((URL) value));
            // Resize image to fit cell size
            Image img = icon.getImage().getScaledInstance(table.getRowHeight(), table.getRowHeight(), Image.SCALE_SMOOTH);
            // Set the image as the icon of the label
            setIcon(new ImageIcon(img));
            // Clear text
            setText(null);
            // Set alignment to center
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        return this;
    }
}
