/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade;
import io.magicthegathering.javasdk.resource.Card;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author joaox
 */
public class CardPrinter {
    
    String[] getCardInfo(Card card) {
        List<String> cardInfo = new ArrayList<>();
        cardInfo.add(Integer.toString(card.getMultiverseid()));
        cardInfo.add(card.getName() + "(" + card.getSetName() + ")" );
        cardInfo.add(card.getManaCost());
        cardInfo.add(card.getType());
        cardInfo.add(card.getText());
        cardInfo.add((card.getPower() != null) ? (card.getPower() + "/" + card.getToughness()) : "");
        cardInfo.add((card.getLoyalty() != null) ? ("[" + card.getLoyalty() + "]") : "");
        cardInfo.add("\033 " + ((card.getFlavor() != null) ? card.getFlavor() : " ") + "\033");
        return cardInfo.toArray(new String[0]);
    }
}

