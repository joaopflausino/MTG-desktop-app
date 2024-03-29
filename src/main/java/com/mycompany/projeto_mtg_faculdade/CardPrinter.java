/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade;
import io.magicthegathering.javasdk.resource.Card;
/**
 *
 * @author joaox
 */
public class CardPrinter {
    void printCard(Card card){
        System.out.println("ID: " + card.getMultiverseid());
        System.out.println(card.getName() + "(" + card.getSetName() + ")    \\n (" + card.getManaCost() + ")");
        System.out.println(card.getType());
        System.out.println(card.getText());
        System.out.println((card.getPower()!=null)?(card.getPower() + "/" + card.getToughness() + "\n"): "");
        System.out.println((card.getLoyalty()!=null)?("[" + card.getLoyalty() + "]" + "\n"): "");
        System.out.println("\033[3m" + ((card.getFlavor()!=null)?card.getFlavor() : " ")+"\033[0m\n");
        System.out.println();
    }
}
