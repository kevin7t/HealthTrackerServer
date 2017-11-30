package com.kevin.HealthTrackerServer.Controller;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kevin.HealthTrackerServer.Datamodels.User;
import com.kevin.HealthTrackerServer.Repository.UserRepository;

@Controller
@RequestMapping (path ="/users")
public class MainController {
    @Autowired
    UserRepository userRepository;


    @RequestMapping(value ="", method = RequestMethod.GET)
    @ResponseBody
    private List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        userRepository.findAll().iterator().forEachRemaining(u-> users.add(u));
        return users;
    }


    @RequestMapping(value ="", method = RequestMethod.POST)
    public ResponseEntity<User> add(@RequestBody User user ){
        User n = new User();
        n.setUserId(UUID.randomUUID().toString());
        n.setName(user.getName());
        n.setPassword(user.getPassword());
        userRepository.save(n);
        user.setUserId(n.getUserId());
        return new ResponseEntity(user,HttpStatus.OK);
    }

    @RequestMapping (value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getUser (@PathVariable ("id") String id) {
        try {
            User user = userRepository.findByUserId(id);
            return new ResponseEntity(user, HttpStatus.OK);
        } catch (NullPointerException n  ){
            return new ResponseEntity("User not found ", HttpStatus.NOT_FOUND);
        }
    }

//    @RequestMapping (value = "/{name}", method = RequestMethod.GET)
//    public ResponseEntity getUserFromName (@PathVariable ("name") String name) {
//        User user;
//        try {
//            user = userRepository.findByName(name);
//            System.out.println(user.toString() +"USER ");
//        } catch (NullPointerException n  ){
//            return new ResponseEntity("User not found ", HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity(user, HttpStatus.OK);
//    }

    @RequestMapping (value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete (@PathVariable ("id") String id) {
        try {
             userRepository.deleteByUserId(id);
        } catch (Exception e ){
            return new ResponseEntity("User not found ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Deleted", HttpStatus.OK);
    }

}
