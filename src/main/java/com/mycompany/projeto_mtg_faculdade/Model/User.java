package com.mycompany.projeto_mtg_faculdade.Model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedUsername() {
        return SHA512(username);
    }

    public String getHashedPassword() {
        return SHA512(password);
    }

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
}
