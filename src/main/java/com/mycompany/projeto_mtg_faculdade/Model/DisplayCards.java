/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Model;

import com.mycompany.projeto_mtg_faculdade.Controller.Download_URL;
import com.mycompany.projeto_mtg_faculdade.Controller.ResizeImage;
import com.mycompany.projeto_mtg_faculdade.Model.Card;
import com.mycompany.projeto_mtg_faculdade.Model.CardPrinter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author joaox
 */
public class DisplayCards {

    private String directoryName = "/Images/ImagesUrl/";

    public int fetcher(String filter, DefaultTableModel tblModel) throws Exception {
        ScryfallAPICall printer = new ScryfallAPICall();
        List<Card> cards = new ArrayList<>();
        CardPrinter printCardsJtable = new CardPrinter();
        File DeleteDir = new File("src/main/resources"+directoryName); 
        int numresult = 0;
        int iconHeight = 0;

       // try {
            String[] myFiles;
            if (DeleteDir.isDirectory()) {
                myFiles = DeleteDir.list();
                for (String myFile1 : myFiles) {
                    File myFile = new File(DeleteDir, myFile1);
                    myFile.delete();
                }
            }
            tblModel.setRowCount(0);
            cards = printer.getAllCards(filter);
            if (cards.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome não encontrado");
            } else {
                Download_URL file = new Download_URL();
                String filepath = file.downloadFile(directoryName,cards);
                for (Card card : cards) {
                    if (card.getId() != null) {                 
                        ResizeImage RI = new ResizeImage();
                        RI.SetTargetWidth(122, 170);
                        Icon resizedIconFromFile = RI.resizeImageFromFile(directoryName + card.getId()+ ".jpg");
                        iconHeight = resizedIconFromFile.getIconHeight();
                        tblModel.addRow(printCardsJtable.getCardInfo(card, resizedIconFromFile));
                        System.out.println(filepath);
                        numresult++;
                    }
                }
            }
            
            return iconHeight;
        }/*catch (Exception e) {
            JOptionPane.showMessageDialog(null, "deu caca\n" + e);
            System.out.println(e);
        }*/
       //return 0;
    }


