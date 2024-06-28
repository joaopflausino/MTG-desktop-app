/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Model;

import java.util.UUID;

/**
 *
 * @author joaox
 */
public class UserLibraryModel {

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCardId() {
        return cardId;
    }

    public void setCardId(UUID cardId) {
        this.cardId = cardId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    private UUID userId;
    private UUID cardId;
    private int quantity;


    public UserLibraryModel(UUID userId, UUID cardId, int quantity, String imageUrl,String name,String manaCost,String typeLine,String oracleText,String set) {
        this.userId = userId;
        this.cardId = cardId;
        this.quantity = quantity;

    }
    
   
}
