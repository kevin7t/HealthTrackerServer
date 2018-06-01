package com.kevin.healthtracker.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.UserDTO;
import com.kevin.healthtracker.server.security.JwtTokenProvider;
import com.kevin.healthtracker.server.service.UserAuthenticationServiceImpl;
import com.kevin.healthtracker.server.service.UserServiceImpl;

@Controller
@RequestMapping(path = "healthtracker/users")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserAuthenticationServiceImpl authenticationService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody User user) {
        UserDTO userDTO = userService.createUser(user);
        String token = jwtTokenProvider.createToken(userDTO.getUserName(), userDTO.getRoles());
        SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
        authenticationService.login(token, user.getUserName());
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> authenticateUser(@RequestBody User user) {
        String token;
        if (userService.authenticateUser(user)) {
            UserDTO userDTO = userService.findByUserName(user.getUserName());
            token = jwtTokenProvider.createToken(userDTO.getUserName(), userDTO.getRoles());
            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
            authenticationService.login(token, user.getUserName());
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
}
