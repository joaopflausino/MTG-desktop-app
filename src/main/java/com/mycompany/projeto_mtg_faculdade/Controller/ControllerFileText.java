/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author joaox
 */
public class ControllerFileText extends ControllerFile {
    private String texto = null;
    private BufferedReader leitor = null;
    private BufferedWriter escritor = null;

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return true caso a operação de leitura seja bem sucedida ou false caso
     * contrário.
     */
    @Override
    public boolean ler() {
        StringBuilder line = new StringBuilder();
        if (arquivo != null) {
            try {
                leitor = new BufferedReader(new FileReader(arquivo));
                while (leitor.ready()) {
                    line.append(leitor.readLine());
                    line.append("\n");
                }
                leitor.close();
                setTexto(line.toString());
                return true;
            } catch (FileNotFoundException erro) {
                //erro.printStackTrace(); //usado para debug
                System.err.println(erro.getMessage() + "Arquivo não encontrado.");
                return false;
            } catch (IOException erro) {
                System.err.println(erro.getMessage() + "Erro ao ler arquivo.");
                return false;
            }
        }
        return false;
    }

    /**
     * @param append se o texto será continuado a partir do seu final (append =
     * false) ou se o arquivo será sobrescrito (append = false)
     * @return true caso a operação de escrita seja bem sucedida ou false caso
     * contrário.
     */
    @Override
    public boolean escrever(boolean append) {
        if (arquivo != null) {
            try {
                escritor = new BufferedWriter(new FileWriter(arquivo+".txt", append));
                escritor.write(getTexto());
                escritor.close();
                return true;
            } catch (IOException erro) {
                System.err.println(erro.getMessage() + "Erro ao ler arquivo.");
                return false;
            }
        } else {
            return false;
        }
    }
}
