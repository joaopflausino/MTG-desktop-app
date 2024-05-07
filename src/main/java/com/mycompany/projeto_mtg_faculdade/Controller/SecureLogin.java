/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;

import com.mycompany.projeto_mtg_faculdade.Model.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author joaox
 */
public class SecureLogin {

    private static String filePath = "src/data/creds.dat";
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;

    public boolean registerUser(String username, String password) throws IOException {
        User user = new User(username, password);
        String hashedUsername = user.getHashedUsername();
        String hashedPassword = user.getHashedPassword();

        if (userExists(hashedUsername)) {
            JOptionPane.showMessageDialog(null, "Username already exists!");
            return false;
        }

        try {
            outputStream = new DataOutputStream(new FileOutputStream(filePath, true));
            outputStream.writeUTF(hashedUsername + "-" + hashedPassword);
            JOptionPane.showMessageDialog(null, "Usuario Cadastrado !!!");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error ao cadastrar usuario \n na classe : %s \n occorreu : %s" + e.getClass().toString() + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public boolean loginUser(String user, String pass) throws IOException {
        String userof = new User(user, "").getHashedUsername();
        String passof = new User("", pass).getHashedPassword();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            reader.skip(2);
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);

                String[] parts = line.split("-");
                String username = parts[0];
                String password = parts[1];
                System.out.println(username + "........." + password);
                System.out.println(userof + "//////" + passof);

                if (userof.equals(username) && passof.equals(password)) {
                    return true;
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return false;
    }


    public boolean updateUser(String oldUsername, String newUsername, String newPassword) throws IOException {
        String hashedOldUsername = new User(oldUsername, "").getHashedUsername();
        String hashedNewUsername = new User(newUsername, newPassword).getHashedUsername();
        String hashedNewPassword = new User("", newPassword).getHashedPassword();
        System.out.println(hashedOldUsername + "........." + hashedNewUsername+ "........" + hashedNewPassword );


        StringBuilder updatedData = new StringBuilder();

        try (DataInputStream reader = new DataInputStream(new FileInputStream(filePath))) {
            while (true) {
                String line = reader.readUTF();
                StringTokenizer tokenizer = new StringTokenizer(line, "-");
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();
                System.out.println(username + "........." + password );

                if (username.equals(hashedOldUsername)) {
                    updatedData.append(hashedNewUsername).append("-").append(hashedNewPassword).append("\n");
                } else {
                    updatedData.append(username).append("-").append(password).append("\n");
                }
            }
        } catch (EOFException e) {
            // End of file reached
        }

        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(filePath))) {
            writer.writeUTF(updatedData.toString());
            JOptionPane.showMessageDialog(null, "User updated successfully!");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteUser(String username) throws IOException {
        String userHash = new User(username, "").getHashedUsername();
        StringBuilder newData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length > 0 && !parts[0].equals(userHash)) {
                    newData.append(line).append("\n");
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(newData.toString());
            JOptionPane.showMessageDialog(null, "User deleted successfully!");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean userExists(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true; // User already exists
                }
            }
            return false; // User not found
        }
    }

}
