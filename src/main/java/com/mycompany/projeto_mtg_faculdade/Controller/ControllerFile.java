/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author joaox
 */
public abstract class ControllerFile {
    protected File arquivo = null;
    public abstract boolean ler();
    public abstract boolean escrever(boolean append);
    
    /**
     * @return the arquivo
     */
    
    public void setArquivo(File filepath) {
        this.arquivo = filepath;
    }
    
    public File getArquivo() {
        return arquivo;
    }

    /**
     * @param TextoBotao o texto para o botão de escolha do usuário
     */
    
    
    public void setAcharArquivo(String TextoBotao) {
        arquivo = null;
        String pastainicial = System.getProperty("user.dir");
        JFileChooser chooser = new JFileChooser(pastainicial);
        if (chooser.showDialog(null, TextoBotao) == JFileChooser.APPROVE_OPTION) {
            arquivo = chooser.getSelectedFile();
        }     
    }
}
