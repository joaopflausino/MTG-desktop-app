/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Model;

/**
 *
 * @author Jomes
 */
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CardListResponse {
    private List<Card> data;
    @SerializedName("has_more")
    private boolean hasMore;
    @SerializedName("next_page")
    private String nextPage;

    // Getters
    public List<Card> getData() {
        return data;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public String getNextPage() {
        return nextPage;
    }
}