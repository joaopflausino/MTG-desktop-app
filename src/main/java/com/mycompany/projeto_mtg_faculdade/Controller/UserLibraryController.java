package com.mycompany.projeto_mtg_faculdade.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mycompany.projeto_mtg_faculdade.Model.LibraryCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserLibraryController {

    private final DatabaseConnectionController dbController;
    private List<LibraryCard> data;
    private String directoryName = "/Images/ImagesUrl/";

    public UserLibraryController(DatabaseConnectionController dbController) {
        this.dbController = dbController;
        if (this.dbController.getConnection() == null) {
            throw new IllegalStateException("Failed to establish database connection.");
        }
    }

public boolean addCardToLibrary(String userId, String cardId, int quantity) {
    String sql = "INSERT INTO user_library (user_id, card_id, quantity) VALUES (?, ?, ?) "
            + "ON CONFLICT (user_id, card_id) DO UPDATE SET quantity = user_library.quantity + EXCLUDED.quantity";
    try (Connection conn = dbController.getConnection()) {
        if (conn == null) {
            throw new SQLException("Failed to establish database connection.");
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // Disable auto-commit mode

            pstmt.setObject(1, UUID.fromString(userId)); // Convert userId to UUID
            pstmt.setObject(2, UUID.fromString(cardId)); // Convert cardId to UUID
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();

            conn.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            conn.rollback(); // Rollback transaction
            throw e; // Re-throw the exception for outer catch to handle
        }
    } catch (SQLException e) {
        System.err.println("Error adding card to library: " + e);
    }
    return false;
}

    public boolean removeCardFromLibrary(String userId, String cardId, int quantity) {
        String sql = "UPDATE user_library SET quantity = quantity - ? WHERE user_id = ? AND card_id = ?";
        try (Connection conn = dbController.getConnection()) {
            if (conn == null) {
                throw new SQLException("Failed to establish database connection.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                conn.setAutoCommit(false); // Disable auto-commit mode

                pstmt.setInt(1, quantity);
                pstmt.setObject(2, UUID.fromString(userId)); // Convert userId to UUID
                pstmt.setObject(3, UUID.fromString(cardId)); // Convert cardId to UUID
                pstmt.executeUpdate();

                // Optionally, you may want to remove the entry if quantity becomes 0 or less
                String cleanUpSql = "DELETE FROM user_library WHERE user_id = ? AND card_id = ? AND quantity <= 0";
                try (PreparedStatement cleanupPstmt = conn.prepareStatement(cleanUpSql)) {
                    cleanupPstmt.setObject(1, UUID.fromString(userId)); // Convert userId to UUID
                    cleanupPstmt.setObject(2, UUID.fromString(cardId)); // Convert cardId to UUID
                    cleanupPstmt.executeUpdate();
                }

                conn.commit(); // Commit transaction
                return true;
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction
                throw e; // Re-throw the exception for outer catch to handle
            }
        } catch (SQLException e) {
            System.err.println("Error removing card from library: " + e);
        }
        return false;
    }

    public boolean deleteRow(String userId, String cardId) {
        String sql = "DELETE FROM user_library WHERE user_id = ? AND card_id = ?";
        try (Connection conn = dbController.getConnection()) {
            if (conn == null) {
                throw new SQLException("Failed to establish database connection.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                conn.setAutoCommit(false); // Disable auto-commit mode

                pstmt.setObject(1, UUID.fromString(userId)); // Convert userId to UUID
                pstmt.setObject(2, UUID.fromString(cardId)); // Convert cardId to UUID
                pstmt.executeUpdate();

                conn.commit(); // Commit transaction
                return true;
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction
                throw e; // Re-throw the exception for outer catch to handle
            }
        } catch (SQLException e) {
            System.err.println("Error deleting row from library: " + e);
        }
        return false;
    }

    public boolean deleteLibrary(String userId) {
        String sql = "DELETE FROM user_library WHERE user_id = ?";
        try (Connection conn = dbController.getConnection()) {
            if (conn == null) {
                throw new SQLException("Failed to establish database connection.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                conn.setAutoCommit(false); // Disable auto-commit mode

                pstmt.setObject(1, UUID.fromString(userId)); // Convert userId to UUID
                pstmt.executeUpdate();

                conn.commit(); // Commit transaction
                return true;
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction
                throw e; // Re-throw the exception for outer catch to handle
            }
        } catch (SQLException e) {
            System.err.println("Error deleting library: " + e);
        }
        return false;
    }

    public List<LibraryCard> getUserLibrary(String userId) {
        String sql = "SELECT ul.card_id, ul.quantity, c.name, c.image_uris, c.set, c.oracle_text FROM user_library ul "
                + "JOIN cards c ON ul.card_id = c.id WHERE ul.user_id = ?";
        data = new ArrayList<>(); // Initialize the data list

        try (Connection conn = dbController.getConnection()) {
            if (conn == null) {
                throw new SQLException("Failed to establish database connection.");
            }
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setObject(1, UUID.fromString(userId)); // Convert userId to UUID
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    LibraryCard card = new LibraryCard();

                    String imageUrisJsonString = rs.getString("image_uris");

                    if (imageUrisJsonString != null) {
                        JsonObject imageUrisJsonObject = new Gson().fromJson(imageUrisJsonString, JsonObject.class);
                        if (imageUrisJsonObject.has("normal")) {
                            String imageUrl = imageUrisJsonObject.get("normal").getAsString();
                            card.setImageUrl(imageUrl);
                        }
                    }
                    card.setName(rs.getString("name"));
                    card.setSet(rs.getString("set"));
                    card.setOracleText(rs.getString("oracle_text"));
                    card.setQuantity(rs.getInt("quantity"));
                    card.setId(rs.getString("card_id"));

                    // Add card to data list
                    data.add(card);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user library: " + e);
        }
        return data;
    }

}
