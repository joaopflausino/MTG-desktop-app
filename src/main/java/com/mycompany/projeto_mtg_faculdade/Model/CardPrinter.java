/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Model;
import com.mycompany.projeto_mtg_faculdade.Model.Card;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
/**
 *
 * @author joaox
 */
public class CardPrinter {
    
    public Object[] getCardInfo(Card card,Icon resizedIconFromFile) {
        List<Object> cardInfo = new ArrayList<>();
        cardInfo.add(resizedIconFromFile);
        cardInfo.add(card.getName());
        cardInfo.add(card.getSet());
        cardInfo.add(card.getOracleText());
        return cardInfo.toArray(new Object[0]);
    }
    
    public Object[] getCardMockInfo(Icon resizedIconFromFile){
        List<Object> cardMock = new ArrayList<>();
        cardMock.add(resizedIconFromFile);
        cardMock.add("card name");
        cardMock.add("card set");
        cardMock.add("Oracle text");
        return cardMock.toArray(new Object[0]);
    }
    
    public Object[] getCardInfoUpdate(Icon resizedIconFromFile,String cardName,String cardSet,String cardText){
        List<Object> cardInfoUpdate = new ArrayList<>();
        cardInfoUpdate.add(resizedIconFromFile);
        cardInfoUpdate.add("card name");
        cardInfoUpdate.add("card set");
        cardInfoUpdate.add("Oracle text");
        return cardInfoUpdate.toArray(new Object[0]);
    }
}

