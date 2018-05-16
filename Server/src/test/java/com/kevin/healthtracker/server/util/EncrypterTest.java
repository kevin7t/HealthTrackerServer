package com.kevin.healthtracker.server.util;

import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.Test;

public class EncrypterTest {

    @Test
    public void authenticate() throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] salt = Encrypter.generateSalt();
        String inputPassword = "testpassword";
        byte[] encryptedPassword = Encrypter.generateHash(inputPassword, salt);
        assertTrue(Encrypter.authenticate(inputPassword, encryptedPassword, salt));
    }

}