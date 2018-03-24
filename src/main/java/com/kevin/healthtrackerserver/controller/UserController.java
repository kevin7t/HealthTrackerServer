package com.kevin.healthtrackerserver.controller;

import java.util.List;

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

@Controller
@ComponentScan
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> add(@RequestBody User user) {
        return new ResponseEntity(userService.createUser(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity authenticateUser(@RequestBody User user) {
        return new ResponseEntity(userService.authenticateUser(user), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<User> changePassword(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") int id) {
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity("User not found ", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }
}
