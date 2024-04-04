/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projeto_mtg_faculdade.Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
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
     private static String filePath = "data/creds";
    private BufferedReader leitor = null;

    private static String SHA512(String hash) {
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("SHA-512");

            byte[] hashDigest = msgDigest.digest(hash.getBytes());

            BigInteger inputDigestBigInt = new BigInteger(1, hashDigest);

            String hashtext = inputDigestBigInt.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean registerUser(String username, String password) throws IOException {

        String hashedUsername = SHA512(username);
        String hashedPassword = SHA512(password);
        System.out.println(filePath);
        
        if (userExists(hashedUsername)) {
            JOptionPane.showMessageDialog(null, "Username already exists!");
            return false;
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(hashedUsername + "-" + hashedPassword);
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Usuario Cadastrado !!!");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error ao cadastrar usuario \n na classe : %s \n occorreu : %s" + e.getClass().toString() + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
    }

    public boolean LoginUser(String user, String pass) throws FileNotFoundException, IOException {
        String userof = SHA512(user);
        String passof = SHA512(pass);
        StringBuilder line = new StringBuilder();

        leitor = new BufferedReader(new FileReader(filePath));
        while (leitor.ready()) {
            line.append(leitor.readLine());
            line.append("\n");
        }
        leitor.close();
        StringTokenizer linha = new StringTokenizer(line.toString(), "\n");
        while (linha.hasMoreTokens()) {
            String getToken = linha.nextToken().trim();
            String username = getToken.substring(0, getToken.indexOf("-"));
            String password = getToken.substring(getToken.indexOf("-") + 1);
            System.out.println(username + password);

            if (userof.equals(username) && passof.equals(password)) {
                return true;
            }
        }
        return false;
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
    
    public boolean updateUser(String oldUsername, String newUsername, String newPassword) throws IOException {
        String hashedOldUsername = SHA512(oldUsername);
        String hashedNewUsername = SHA512(newUsername);
        String hashedNewPassword = SHA512(newPassword);

        // Check if the user exists
        if (!userExists(hashedOldUsername)) {
            JOptionPane.showMessageDialog(null, "User does not exist!");
            return false;
        }
        // Create a temporary file to store updated credentials
        return false;
    }

}
