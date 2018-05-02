package com.kevin.healthtracker.server.controller;

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

import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.server.service.UserFeedServiceImpl;

@Controller
@RequestMapping(path = "healthtracker/feed")
public class UserFeedController {

    @Autowired
    UserFeedServiceImpl userFeedService;

    @RequestMapping(value = "/status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addStatus(@RequestBody Status status) {
        return new ResponseEntity(userFeedService.createStatus(status), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/status/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getStatus(@PathVariable("userId") int userId) {
        return new ResponseEntity(userFeedService.getStatusesByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/status/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStatus(@PathVariable("id") int id) {
        userFeedService.deleteStatusById(id);
    }

    @RequestMapping(value = "/status/like/{statusId}/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addLike(@PathVariable("statusId") int statusId, @PathVariable("userId") int userId) {
        userFeedService.addLikeToStatus(statusId, userId);
    }

    @RequestMapping(value = "/status/like/{statusId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getLikes(@PathVariable("statusId") int id) {
        return new ResponseEntity(userFeedService.getLikesFromStatus(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/status/like/{statusId}/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void removeLike(@PathVariable("statusId") int statusId, @PathVariable("userId") int userId) {
        userFeedService.removeLikeFromStatus(statusId, userId);
    }
}
