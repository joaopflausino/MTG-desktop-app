/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author joaox
 */
public class ControllerFileTextDeck extends ControllerFileText {
    
    private  DefaultTableModel table;

    public ControllerFileTextDeck(DefaultTableModel table) {
        this.table = table;
    }

    public boolean ReadDeck() {
        if (ler()) {
            String conteudo = getTexto();
            StringTokenizer linha = new StringTokenizer(conteudo, " ");
            if (linha.countTokens() >= 2) {
                String quantity = linha.nextToken();
                StringBuilder name = new StringBuilder();
                while (linha.hasMoreTokens()) {
                    name.append(linha.nextToken()).append(" ");
                }
                table.addRow(new Object[]{quantity, name.toString().trim()});
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean WriteDeck(boolean append) {
        boolean aux = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                sb.append(table.getValueAt(i, j).toString());
                if (j < table.getColumnCount() - 1) {
                    sb.append(" "); // Space separator
                }
            }
            sb.append(System.lineSeparator()); // New line separator
        }
        setTexto(sb.toString());
        aux = (escrever(append));

        return aux;
        }

    /**
     * @return the clientes
     */

    /**
     * @param clientes the clientes to set
     */

}
