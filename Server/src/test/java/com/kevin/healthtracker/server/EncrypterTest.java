package com.kevin.healthtracker.server;

import com.kevin.healthtracker.server.util.Encrypter;
import org.junit.Test;


public class EncrypterTest {
    @Test
    public void encryptionIsCorrect() throws Exception {
        Encrypter passwordEncrypter = new Encrypter();
        byte[] salt = Encrypter.generateSalt();
        String inputPassword = "testpassword";
        byte[] encryptedPassword = Encrypter.generateHash(inputPassword, salt);
        Encrypter.authenticate(inputPassword, encryptedPassword, salt);
    }
}
