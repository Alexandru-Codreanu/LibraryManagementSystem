package com.project.librarymanagementsystem.utils;

import java.security.MessageDigest;
import java.util.Base64;

public class SHA512Hashing {
    public static String hash(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] hashedPassword = md.digest(password.getBytes());

        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] hashedInputPassword = md.digest(inputPassword.getBytes());

        String newHash = Base64.getEncoder().encodeToString(hashedInputPassword);

        return newHash.equals(storedHash);
    }
}