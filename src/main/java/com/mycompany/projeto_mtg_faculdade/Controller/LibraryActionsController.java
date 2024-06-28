package com.mycompany.projeto_mtg_faculdade.Controller;

import com.mycompany.projeto_mtg_faculdade.Model.Card;
import com.mycompany.projeto_mtg_faculdade.Model.LibraryCard;
import com.mycompany.projeto_mtg_faculdade.Model.UserLibraryModel;
import com.mycompany.projeto_mtg_faculdade.View.TradicionalSearchForm;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.mycompany.projeto_mtg_faculdade.Model.CardPrinter;
import java.io.File;
import java.util.ArrayList;

public class LibraryActionsController {

    private UserLibraryController userLibraryController;
    private String directoryName = "/Images/ImagesUrl/";

    public LibraryActionsController(UserLibraryController userLibraryController) {
        this.userLibraryController = userLibraryController;
    }

    public List<LibraryCard> loadData(String userId, JTable table) {
        List<LibraryCard> library = new ArrayList<>();
        DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
        CardPrinter printCardsJtable = new CardPrinter();

        File DeleteDir = new File("src/main/resources" + directoryName);
        int numresult = 0;

        String[] myFiles;
        if (DeleteDir.isDirectory()) {
            myFiles = DeleteDir.list();
            for (String myFile1 : myFiles) {
                File myFile = new File(DeleteDir, myFile1);
                myFile.delete();
            }
        }
 
        tblModel.setRowCount(0);
        library = userLibraryController.getUserLibrary(userId);
        System.out.println("//////////////////////////////");

        try {
            Download_URL file = new Download_URL();
            for (LibraryCard card : library) {
                String filepath = file.downloadFile(directoryName, card.getImageUrl(), card.getId());
                ResizeImage RI = new ResizeImage();
                RI.SetTargetWidth(122, 170);
                Icon resizedIconFromFile = RI.resizeImageFromFile(directoryName + card.getId() + ".jpg");
                System.out.println("//////////////////////////////");
                System.out.println(card.getName());
                System.out.println(card.getImageUrl());
                tblModel.addRow(printCardsJtable.getCardLibraryInfo(card, resizedIconFromFile));
                System.out.println(filepath);

                numresult++;
            }

            table.getColumnModel().getColumn(0).setCellRenderer(new ImageRender());
            table.setRowHeight(100);
        } catch (Exception ex) {
            Logger.getLogger(TradicionalSearchForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return library;
    }


}
