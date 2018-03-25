package util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Encrypter {
    public static boolean authenticate(String attemptedPassword, byte[] actualHash, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] encryptedAttemptedPassword = generateHash(attemptedPassword, salt);
        System.out.println("Attempted password " + attemptedPassword);
        System.out.println("Encrypted password to string: " + String.format("%x", new BigInteger(generateHash(attemptedPassword, salt))));
        return Arrays.equals(actualHash, encryptedAttemptedPassword);
    }

    public static byte[] generateHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160;
        int iterations = 10000;

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        return f.generateSecret(spec).getEncoded();
    }

    public static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


}
