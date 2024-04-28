/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ScryfallAPICall {
    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Gson gson = new Gson();

    public List<Card> getAllCards(String cardName) throws Exception {
        List<Card> allCards = new ArrayList<>();
        String url = "https://api.scryfall.com/cards/search?q=%2B%2B" + cardName;

        while (url != null) {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to make API call: " + response);
            }

            String responseBody = response.body().string();
            // Parse JSON response
            CardListResponse cardListResponse = gson.fromJson(responseBody, CardListResponse.class);
            allCards.addAll(cardListResponse.getData());

            // Get the URL of the next page, if available
            url = cardListResponse.getNextPage();
        }

        return allCards;
    }
}