package com.kevin.healthtrackerserver.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kevin.healthtrackerserver.datamodels.User;
import com.kevin.healthtrackerserver.service.UserService;
import com.kevin.healthtrackerserver.util.Encrypter;

@Controller
@ComponentScan
@RequestMapping(path = "/users")
public class UserController {


    @Autowired
    UserService userService;
    //    //TODO https://www.concretepage.com/spring-boot/spring-boot-rest-jpa-hibernate-mysql-example



//    @RequestMapping(value = "", method = RequestMethod.GET)
//    @ResponseBody
//    private List<User> getAllUsers() {
//        List<User> users = new ArrayList<User>();
//        userRepository.findAll().iterator().forEachRemaining(u -> users.add(u));
//        return users;
//    }


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<User> add(@RequestBody User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Encrypter.generateSalt();
        User n = new User();
        n.setUserName(user.getUserName());
        n.setHash(Encrypter.getEncryptedPassword(user.getPassword(), salt));
        n.setSalt(salt);

        return new ResponseEntity(userService.createUser(n), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable("id") int id) {
        try {
            User user = userService.findById(id);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (NullPointerException n) {
            return new ResponseEntity("User not found ", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity authenticateUser(@RequestBody User userInfo) throws NoSuchAlgorithmException, InvalidKeySpecException {

//        User user = userRepository.findByUserName(userInfo.getUserName());
//        if (new String(Encrypter.getEncryptedPassword(userInfo.getPassword(), user.getSalt())).equals(user.getPassword())) {
//            return new ResponseEntity("Authenticated", HttpStatus.OK);
//        }

        //get user salt from database, create hash from user password and compare hash from database

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) {
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity("User not found ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }
}
