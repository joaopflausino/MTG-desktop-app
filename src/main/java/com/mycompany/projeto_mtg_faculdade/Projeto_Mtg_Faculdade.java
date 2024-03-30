/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.projeto_mtg_faculdade;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author joaox
 */
public class Projeto_Mtg_Faculdade {
    
    public static List <Card> cards = new ArrayList<>();
    public static final Scanner sc = new Scanner(System.in);
    

    public static void main(String[] args) {
     
     CardPrinter printer = new CardPrinter();
     int numresult = 0;
     System.out.println("coloque o nome em ingles:");
     String filter = "name=" + sc.next();
     try{
         cards = CardAPI.getAllCards(List.of(filter));
         if(cards.isEmpty()){
             System.out.println("0 resultados encontrados");
         }else{
             for(Card c:cards){
                 
                 if(c.getMultiverseid() != -1){
                     
                     numresult++;
                 }
                 
             }
         }
     }catch(Exception e){
         System.out.println("deu caca" + e);
     }
    }
}
