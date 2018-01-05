package com.example.kevin.finalyearproject;

import com.kevin.HealthTrackerServer.Util.Encrypter;
import org.junit.Test;

public class EncrypterTest {
    @Test
    public void encryptionIsCorret() throws Exception {
        Encrypter passwordEncrypter = new Encrypter();
        byte[] salt = passwordEncrypter.generateSalt();
        String inputPassword = "testpassword";
        byte[] encryptedPassword = passwordEncrypter.getEncryptedPassword(inputPassword, salt);
        passwordEncrypter.authenticate(inputPassword, encryptedPassword, salt);
    }
}
