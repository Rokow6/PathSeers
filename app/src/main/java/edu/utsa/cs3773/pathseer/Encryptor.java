package edu.utsa.cs3773.pathseer;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import edu.utsa.cs3773.pathseer.data.UserDao;

public class Encryptor {

    // Returns hashed password
    public static String encryptString(String input, int userID, UserDao userDao) throws NoSuchAlgorithmException {
        String salt = getSaltString();

        userDao.updateSalt(userID, salt);
        Log.d("Encryptor", "Generated salt for userID " + userID + ": " + salt);
        String salted = input + salt;

        MessageDigest md = MessageDigest.getInstance("MD5");

        byte[] messageDigest = md.digest(salted.getBytes());

        BigInteger bigInt = new BigInteger(1, messageDigest);

        return bigInt.toString(16);
    }
    //Hash an entered password with an existing salt
    public static String encryptString(String password, String salt) throws NoSuchAlgorithmException {
        String salted = password + salt;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(salted.getBytes());
        BigInteger bigInt = new BigInteger(1, messageDigest);
        return bigInt.toString(16);

    }
    // Generates random string for hash
    private static String getSaltString() {
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
