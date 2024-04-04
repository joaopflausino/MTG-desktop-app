/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joaox
 */
public class AddToTable {

   public void add(int selectedRow,DefaultTableModel tblModel,DefaultTableModel  tblModel2) {
       try{
           if (selectedRow != -1) {
               Object selectedItem = tblModel.getValueAt(selectedRow, 0);
               boolean itemExists = false;
               int rowCount = tblModel2.getRowCount();

               for (int i = 0; i < rowCount; i++) {
                   Object itemName = tblModel2.getValueAt(i, 1);
                   if (itemName.equals(selectedItem)) {
                       // Item already exists, update quantity
                       int currentQuantity = parseQuantity(tblModel2.getValueAt(i, 0));
                       if (isSevenDwarves(selectedItem) && currentQuantity == 7) {
                           JOptionPane.showMessageDialog(null, "A carta Sete Anões não pode ter mais de 7 cópias\n");
                       }  else if (currentQuantity < 4 || isSpecialItem(selectedItem) || isSevenDwarves(selectedItem)) {
                           tblModel2.setValueAt(currentQuantity + 1, i, 0);
                       }else if (currentQuantity == 4) {
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
       }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Problema para adicionar as Cartas\n" + e);
       }
    }
   
    private boolean isSpecialItem(Object itemName) {
        // Check if the item is one of the special items
        String[] specialItems = {"Island", "Mountain", "Forest", "Plains", "Swamp","Dragon's Approach","Persistent Petitioners","Rat Colony","Relentless Rats","Shadowborn Apostle","Slime Against Humanity","Seven Dwarves"};
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
