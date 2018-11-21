package com.kevin.healthtracker.server.controller;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.service.FriendServiceImpl;
import com.kevin.healthtracker.server.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "healthtracker/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FriendServiceImpl friendService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.authenticateUser(user), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/changepassword", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> changePassword(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
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

    @RequestMapping(value = "/deletefriend/{user1}/{user2}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteFriend(@PathVariable int user1, @PathVariable int user2) {
        friendService.deleteFriendRelation(user1, user2);
    }

    @RequestMapping(value = "/getoutboundrequests/{user}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Friend>> getOutboundRequests(@PathVariable int user) {
        return new ResponseEntity<>(friendService.getOutboundPendingRequestsForUser(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/getinboundoutboundrequests/{user}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Friend>> getInboundOutboundRequests(@PathVariable int user) {
        return new ResponseEntity<>(friendService.getInboundOutboundPendingRequestsForUser(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/getallrelations/{user}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<User>> getAllRelations(@PathVariable int user) {
        return new ResponseEntity<>(friendService.getFriendRelationList(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/getfriend/{user1}/{user2}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Friend> getFriend(@PathVariable int user1, @PathVariable int user2) {
        return new ResponseEntity<>(friendService.getFriendRelation(user1, user2), HttpStatus.OK);
    }

    @RequestMapping(value = "/searchuser/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> getUserByUsername (@PathVariable String username){
        return new ResponseEntity<>(userService.getUserByUsername(username),HttpStatus.OK);
    }

    @RequestMapping(value = "userscore/{userId}/{score}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> increaseUserScore(@PathVariable int userId, @PathVariable int score) {
        return new ResponseEntity<>(userService.increaseUserScore(userId, score), HttpStatus.OK);
    }
}
