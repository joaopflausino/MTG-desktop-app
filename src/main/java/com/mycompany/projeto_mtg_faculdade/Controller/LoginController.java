package com.mycompany.projeto_mtg_faculdade.Controller;

import com.mycompany.projeto_mtg_faculdade.Model.User;
import com.mycompany.projeto_mtg_faculdade.Controller.DatabaseConnectionController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.swing.JOptionPane;

public class LoginController {

    private DatabaseConnectionController dbController;

    public LoginController() {
        dbController = new DatabaseConnectionController();
    }

    public boolean registerUser(String username, String password) {
        if (dbController.createConnection()) {
            Connection connection = dbController.getConnection();
            User user = new User(username, password);
            String hashedUsername = user.getHashedUsername();
            String hashedPassword = user.getHashedPassword();

            if (userExists(hashedUsername, connection)) {
                JOptionPane.showMessageDialog(null, "Username already exists!");
                dbController.closeConnection();
                return false;
            }

            String sql = "INSERT INTO public.users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, hashedUsername);
                stmt.setString(2, hashedPassword);
                stmt.executeUpdate();
                connection.commit();
                JOptionPane.showMessageDialog(null, "User registered successfully!");
                return true;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error registering user: " + e.getMessage());
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                return false;
            } finally {
                dbController.closeConnection();
            }
        }
        return false;
    }

public String loginUser(String username, String password) {
    String userId = null;
    if (dbController.createConnection()) {
        Connection connection = dbController.getConnection();
        String hashedUsername = new User(username, "").getHashedUsername();
        String hashedPassword = new User("", password).getHashedPassword();

        String sql = "SELECT user_id FROM public.users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hashedUsername);
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userId = rs.getString("user_id"); // Retrieve user_id from the result set
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error logging in: " + e.getMessage());
            e.printStackTrace();
        } finally {
            dbController.closeConnection();
        }
    }
    return userId;
}

    public boolean updateUser(String oldUsername, String newUsername, String newPassword) {
        if (dbController.createConnection()) {
            Connection connection = dbController.getConnection();
            String hashedOldUsername = new User(oldUsername, "").getHashedUsername();
            String hashedNewUsername = new User(newUsername, newPassword).getHashedUsername();
            String hashedNewPassword = new User("", newPassword).getHashedPassword();

            String sql = "UPDATE public.users SET username = ?, password = ? WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, hashedNewUsername);
                stmt.setString(2, hashedNewPassword);
                stmt.setString(3, hashedOldUsername);
                int rowsAffected = stmt.executeUpdate();
                connection.commit();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "User updated successfully!");
                    return true;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error updating user: " + e.getMessage());
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            } finally {
                dbController.closeConnection();
            }
        }
        return false;
    }

    public boolean deleteUser(String username) {
        if (dbController.createConnection()) {
            Connection connection = dbController.getConnection();
            String hashedUsername = new User(username, "").getHashedUsername();

            String sql = "DELETE FROM public.users WHERE username = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, hashedUsername);
                int rowsAffected = stmt.executeUpdate();
                connection.commit();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "User deleted successfully!");
                    return true;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage());
                e.printStackTrace();
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            } finally {
                dbController.closeConnection();
            }
        }
        return false;
    }

    private boolean userExists(String username, Connection connection) {
        String sql = "SELECT * FROM public.users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error checking if user exists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}