package com.harkka.livegreen.logic;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Scrambler {

    //scrambledPassword
    //returns sha512 and salt scrambled password
    public static String scrambledPassword(String passwordToHash, String seed){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(getSalt(seed));
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    //getSalt
    //returns salt
    private static byte[] getSalt(String seed) throws NoSuchAlgorithmException {
        /*SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
         */

        MessageDigest md = MessageDigest.getInstance("MD5");
        return md.digest(seed.getBytes());


    }
}
