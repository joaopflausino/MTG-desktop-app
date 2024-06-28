/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;

import com.mycompany.projeto_mtg_faculdade.Controller.DatabaseConnectionController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author joaox
 */
public class CardsDatabseConnection {

    private DatabaseConnectionController dbController;

    public CardsDatabseConnection() {
        dbController = new DatabaseConnectionController();
    }

    public boolean FindCard(String cardName) {
        if (dbController.createConnection()) {
            Connection connection = dbController.getConnection();

            String sql = "SELECT * FROM public.cards WHERE name = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, cardName);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error logging in: " + e.getMessage());
                e.printStackTrace();
            } finally {
                dbController.closeConnection();
            }
        }
        return false;
    }
}
