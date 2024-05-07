/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;

import com.mycompany.projeto_mtg_faculdade.Model.CardPrinter;
import com.mycompany.projeto_mtg_faculdade.Model.DisplayCards;
import com.mycompany.projeto_mtg_faculdade.View.TradicionalSearchForm;
import com.mycompany.projeto_mtg_faculdade.View.UpdateSearchForm;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jomes
 */
public class TableActions {

    private DefaultTableModel tableModel;
    private String directoryName = "/Images/";
    
    
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void removeFromTable(JTable table) {
        tableModel = (DefaultTableModel) table.getModel();
        int[] selectedRows = table.getSelectedRows();
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int selectedRow = table.convertRowIndexToModel(selectedRows[i]); // Convert view index to model index
            tableModel.removeRow(selectedRow);
        }
    }

    public void AddItem(JTable table1, JTable table2) {
        int selectedRow = table1.getSelectedRow();
        DefaultTableModel tblModel = (DefaultTableModel) table1.getModel();
        DefaultTableModel tblModel2 = (DefaultTableModel) table2.getModel();
        add(selectedRow, tblModel, tblModel2);
    }

    public void SaveTable(JTable table) {
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        ControllerFileTextDeck controler = new ControllerFileTextDeck(tblModel);
        controler.setAcharArquivo("salvar");
        controler.WriteDeck(true);
    }

    public void ReadFileToTable(JTable table) {
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        ControllerFileTextDeck controler = new ControllerFileTextDeck(tblModel);
        tblModel.setRowCount(0);
        controler.setAcharArquivo("Abrir");
        controler.ReadDeck();
    }

    public void SearchAddToTable(JTable table, JTextField jTextField) {
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        String filter = "name=" + jTextField.getText();
        DisplayCards display = new DisplayCards();
        try {
            display.fetcher(filter, tblModel);
            table.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
            table.setRowHeight(100);
        } catch (Exception ex) {
            Logger.getLogger(TradicionalSearchForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
 
    public void AddMockCard(JTable table) throws IOException {
        int iconHeight = 0;
        CardPrinter addCard = new CardPrinter();
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        ResizeImage RI = new ResizeImage();
        RI.SetTargetWidth(122, 170);
        Icon resizedIconFromFile = RI.resizeImageFromFile(directoryName + "Mock_Card" + ".jpg");
        iconHeight = resizedIconFromFile.getIconHeight();
        table.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
        table.setRowHeight(100);
        tblModel.addRow(addCard.getCardMockInfo(resizedIconFromFile));

    }
    
 

    private void add(int selectedRow, DefaultTableModel tblModel, DefaultTableModel tblModel2) {
        try {
            if (selectedRow != -1) {
                Object selectedItem = tblModel.getValueAt(selectedRow, 1);
                boolean itemExists = false;
                int rowCount = tblModel2.getRowCount();

                for (int i = 0; i < rowCount; i++) {
                    Object itemName = tblModel2.getValueAt(i, 1);
                    if (itemName.equals(selectedItem)) {
                        // Item already exists, update quantity
                        int currentQuantity = parseQuantity(tblModel2.getValueAt(i, 0));
                        if (isSevenDwarves(selectedItem) && currentQuantity == 7) {
                            JOptionPane.showMessageDialog(null, "A carta Sete Anões não pode ter mais de 7 cópias\n");
                        } else if (currentQuantity < 4 || isSpecialItem(selectedItem) || isSevenDwarves(selectedItem)) {
                            tblModel2.setValueAt(currentQuantity + 1, i, 0);
                        } else if (currentQuantity == 4) {
                            JOptionPane.showMessageDialog(null, "Não é Possível adicionar mais de 4 cópias\n");
                        }
                        itemExists = true;
                        break;
                    }
                }
                if (!itemExists) {
                    // Item does not exist, add a new row
                    tblModel2.addRow(new Object[]{1, selectedItem});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema para adicionar as Cartas\n" + e);
        }
    }

    private boolean isSpecialItem(Object itemName) {
        // Check if the item is one of the special items
        String[] specialItems = {"Island", "Mountain", "Forest", "Plains", "Swamp", "Dragon's Approach", "Persistent Petitioners", "Rat Colony", "Relentless Rats", "Shadowborn Apostle", "Slime Against Humanity", "Seven Dwarves"};
        for (String specialItem : specialItems) {
            if (itemName.equals(specialItem)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSevenDwarves(Object itemName) {
        // Check if the item is one of the special items
        String[] specialItems = {"Seven Dwarves"};
        for (String specialItem : specialItems) {
            if (itemName.equals(specialItem)) {
                return true;
            }
        }
        return false;
    }

    private int parseQuantity(Object quantity) {
        // Parse quantity to integer
        try {
            return Integer.parseInt(quantity.toString());
        } catch (NumberFormatException e) {
            // Handle parsing error, for example:
            e.printStackTrace();
            return 0; // Return a default value or handle it appropriately
        }
    }
}
