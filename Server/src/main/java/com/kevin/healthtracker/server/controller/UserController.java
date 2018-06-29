package com.kevin.healthtracker.server.controller;

import java.util.List;

import com.kevin.healthtracker.datamodels.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.UserDTO;
import com.kevin.healthtracker.server.service.FriendServiceImpl;
import com.kevin.healthtracker.server.service.UserServiceImpl;

@Controller
@RequestMapping(path = "healthtracker/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FriendServiceImpl friendService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> add(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Boolean> authenticateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.authenticateUser(user), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> changePassword(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.FOUND);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        userService.deleteById(id);
    }

    @RequestMapping(value = "/addfriend/{user1}/{user2}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Friend> addFriend(@PathVariable int user1, @PathVariable int user2) {
        return new ResponseEntity<>(friendService.addFriendRelation(user1, user2), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/acceptfriend/{user1}/{user2}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Friend> acceptFriend(@PathVariable int user1, @PathVariable int user2) {
        return new ResponseEntity<>(friendService.acceptFriendRelation(user1, user2), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/declinefriend/{user1}/{user2}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Friend> declineFriend(@PathVariable int user1, @PathVariable int user2) {
        return new ResponseEntity<>(friendService.declineFriendRelation(user1, user2), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/deletefriend/{user1}/{user2}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteFriend(@PathVariable int user1, @PathVariable int user2) {
        friendService.deleteFriendRelation(user1, user2);
    }

}
