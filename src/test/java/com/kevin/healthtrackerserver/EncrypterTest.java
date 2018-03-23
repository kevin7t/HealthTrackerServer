package com.kevin.healthtrackerserver;

import org.junit.Test;

import com.kevin.HealthTrackerServer.Util.Encrypter;

public class EncrypterTest {
    @Test
    public void encryptionIsCorrect() throws Exception {
        Encrypter passwordEncrypter = new Encrypter();
        byte[] salt = passwordEncrypter.generateSalt();
        String inputPassword = "testpassword";
        byte[] encryptedPassword = passwordEncrypter.getEncryptedPassword(inputPassword, salt);
        passwordEncrypter.authenticate(inputPassword, encryptedPassword, salt);
    }
}
