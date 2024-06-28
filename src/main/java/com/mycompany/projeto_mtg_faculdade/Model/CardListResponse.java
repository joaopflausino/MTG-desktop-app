/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Model;

/**
 *
 * @author Jomes
 */
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mycompany.projeto_mtg_faculdade.Controller.DatabaseConnectionController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
public class CardListResponse {

    private List<Card> data;
    private DatabaseConnectionController dbController;

    public CardListResponse() {
        dbController = new DatabaseConnectionController();
    }

    // Getters
    public List<Card> getData(String cardName) {
        data = new ArrayList<>(); // Initialize the data list
        if (dbController.createConnection()) {
            Connection connection = dbController.getConnection();

            String sql = "SELECT * FROM cards WHERE name ILIKE ? AND layout = 'normal'";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1,"%"+ cardName+"%");
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    // Create a new Card object for each row
                    Card card = new Card();
                    card.setName(resultSet.getString("name"));
                    card.setManaCost(resultSet.getString("mana_cost"));
                    card.setTypeLine(resultSet.getString("type_line"));
                    card.setOracleText(resultSet.getString("oracle_text"));
                    card.setSet(resultSet.getString("set"));
                    card.setId(resultSet.getString("id"));
                    
                    String imageUrisJsonString = resultSet.getString("image_uris");

                    if (imageUrisJsonString != null) {
                        JsonObject imageUrisJsonObject = new Gson().fromJson(imageUrisJsonString, JsonObject.class);
                        if (imageUrisJsonObject.has("normal")) {
                            String imageUrl = imageUrisJsonObject.get("normal").getAsString();
                            card.setImageUrl(imageUrl);
                        }
                    }
                    // Add the Card object to the list
                    data.add(card);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error finding in: " + e.getMessage());
                e.printStackTrace();
            } finally {
                dbController.closeConnection();
            }
        }
        return data; // Return the populated data list
    }
}



