package com.kevin.healthtrackerserver;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kevin.HealthTrackerServer.Datamodels.User;
import com.kevin.HealthTrackerServer.Repository.UserRepository;
import com.kevin.HealthTrackerServer.Util.Encrypter;
import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@Slf4j
public class UserControllerTest {
    @Autowired
    UserRepository userRepository;

    private final String test = "Test";

    @Test
    public void Register() throws Exception {
        byte[] salt = Encrypter.generateSalt();
        User n = new User();
        n.setUserId(UUID.randomUUID().toString());
        n.setUserName(test);
        n.setPassword(Arrays.toString(Encrypter.getEncryptedPassword(test, salt)));
        n.setSalt(salt);
        userRepository.save(n);

        assertNotNull(userRepository.findByUserName(test));

        User nn = userRepository.findByUserName(test);
        byte[] password = Encrypter.getEncryptedPassword(test, nn.getSalt());
        assertEquals(password, nn.getPassword());

    }

    @Test
    public void Authenticate() throws InvalidKeySpecException, NoSuchAlgorithmException {


    }


}