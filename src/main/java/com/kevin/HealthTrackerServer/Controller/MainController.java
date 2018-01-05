package com.kevin.HealthTrackerServer.Controller;


import com.kevin.HealthTrackerServer.Datamodels.User;
import com.kevin.HealthTrackerServer.Repository.UserRepository;
import com.kevin.HealthTrackerServer.Util.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@ComponentScan
@RequestMapping(path = "/users")
public class MainController {
    @Autowired
    UserRepository userRepository;


    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        userRepository.findAll().iterator().forEachRemaining(u -> users.add(u));
        return users;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> add(@RequestBody User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Encrypter.generateSalt();
        User n = new User();
        n.setUserId(UUID.randomUUID().toString());
        n.setUserName(user.getUserName());
        n.setPassword(new String(Encrypter.getEncryptedPassword(user.getPassword(), salt)));
        n.setSalt(salt);
        userRepository.save(n);
        user.setUserId(n.getUserId());
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable("id") String id) {
        try {
            User user = userRepository.findByUserName(id);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (NullPointerException n) {
            return new ResponseEntity("User not found ", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity authenticateUser(@RequestBody User userInfo) throws NoSuchAlgorithmException, InvalidKeySpecException {

        User user = userRepository.findByUserName(userInfo.getUserName());
        if (new String(Encrypter.getEncryptedPassword(userInfo.getPassword(), user.getSalt())).equals(user.getPassword())) {
            return new ResponseEntity(user, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") String id) {
        try {
            userRepository.deleteByUserId(id);
        } catch (Exception e) {
            return new ResponseEntity("User not found ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

}
