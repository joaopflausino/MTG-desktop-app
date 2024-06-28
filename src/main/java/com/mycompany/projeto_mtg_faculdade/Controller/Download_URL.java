/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;
import com.google.common.base.Charsets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.common.io.Resources;
import com.mycompany.projeto_mtg_faculdade.Model.Card;
import java.util.List;

public class Download_URL {

    private final String fromSourceToResource = "src/main/resources/";

    public String downloadFile(String directoryName,String ImageUrl,String cardId) throws IOException {
        // Make sure that this directory exists

        try {
            System.out.println(ImageUrl);
            System.out.println(cardId);
            if (ImageUrl != null) {
                saveFileFromUrlWithJavaIO(
                        fromSourceToResource + directoryName + cardId + ".jpg", ImageUrl);
                System.out.println("Download finished");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("vo sair da funcao");
        return null;
    }

    // Using Java IO
    private static void saveFileFromUrlWithJavaIO(String fileName, String fileUrl)
    throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try { in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(fileName);
            byte data[] = new byte[1024];
            int count;
            while ((count = in .read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if ( in != null)
                in .close();
            if (fout != null)
                fout.close();
        }
    }
}