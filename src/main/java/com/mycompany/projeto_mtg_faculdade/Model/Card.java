/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Model;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 *
 * @author Jomes
 */
public class Card {
    private String name;
    @SerializedName("mana_cost")
    private String manaCost;
    @SerializedName("type_line")
    private String typeLine;
    @SerializedName("oracle_text")
    private String oracleText;
    @SerializedName("image_uris")
    private ImageUris imageUris;
    @SerializedName("set")
    private  String set;
   @SerializedName("id")
    private String id;

    // Getters
    public String getName() {
        return name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public String getTypeLine() {
        return typeLine;
    }

    public String getOracleText() {
        return oracleText;
    }
    
    public String getSet(){
        return set;
    }
    
    public String getId() {
        return id;
    }

    public String getImageUrl() {
        if (imageUris != null) {
            return imageUris.getNormal();
        } else {
            return null;
        }
    }
    
    static class ImageUris {

        private String normal;

        // Getter for normal image URL
        public String getNormal() {
            return normal;
        }
    }
}

