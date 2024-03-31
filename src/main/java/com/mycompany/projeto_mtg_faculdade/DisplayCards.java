/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author joaox
 */
public class DisplayCards {
    
    public void fetcher(String filter, DefaultTableModel tblModel){
        CardPrinter printer = new CardPrinter();
        int numresult = 0;
        List<Card> cards = new ArrayList<>();

        try {
            tblModel.setRowCount(0);
            cards = CardAPI.getAllCards(List.of(filter));
            if (cards.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome não encontrado");
            } else {
                for (Card c : cards) {
                    if (c.getMultiverseid() != -1) {
                        tblModel.addRow(printer.getCardInfo(c));
                        numresult++;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "deu caca" + e);
        }
    }
    
    
}
