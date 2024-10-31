package edu.utsa.cs3773.pathseer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
public class Encryptor {

    // Returns hashed password
    public String encryptString(String input) throws NoSuchAlgorithmException {
        String salt = getSaltString(); //need to store salt in database
        String salted = input + salt;

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(salted.getBytes());

        BigInteger bigInt = new BigInteger(1, messageDigest);

        return bigInt.toString(16);
    }

    // Generates random string for hash
    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        //String saltStr = salt.toString();
        return salt.toString();
    }
}
