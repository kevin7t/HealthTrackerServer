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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kevin.healthtracker.datamodels.dto.LikeDTO;
import com.kevin.healthtracker.datamodels.dto.ReplyDTO;
import com.kevin.healthtracker.datamodels.dto.StatusDTO;
import com.kevin.healthtracker.server.service.UserFeedServiceImpl;

@Controller
@RequestMapping(path = "healthtracker/feed")
public class UserFeedController {

    @Autowired
    UserFeedServiceImpl userFeedService;


    @RequestMapping(value = "/status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addStatus(@RequestBody StatusDTO statusDTO) {
        return new ResponseEntity(userFeedService.createStatus(statusDTO), HttpStatus.CREATED);
    }

    //Needs ?page=pageNumber in url
    @RequestMapping(value = "/status/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getStatus(@PathVariable("userId") int userId, @RequestParam("page") int pageNumber) {
        return new ResponseEntity(userFeedService.getStatusesByUserId(userId, pageNumber), HttpStatus.OK);
    }

    @RequestMapping(value = "/status/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStatus(@PathVariable("id") int id) {
        userFeedService.deleteStatusById(id);
    }

    @RequestMapping(value = "/status/like", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addLike(@RequestBody LikeDTO likeDTO) {
        return new ResponseEntity(userFeedService.addLikeToStatus(likeDTO), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/status/like/{statusId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getLikes(@PathVariable("statusId") int id) {
        return new ResponseEntity(userFeedService.getLikesFromStatus(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/status/like/{statusId}/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeLike(@PathVariable("statusId") int statusId, @PathVariable("userId") int userId) {
        userFeedService.removeLikeFromStatus(statusId, userId);
    }

    @RequestMapping(value = "/status/reply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity sendReply(@RequestBody ReplyDTO replyDTO) {
        return new ResponseEntity(userFeedService.addReplyToStatus(replyDTO), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/status/reply/{statusId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getReplies(@PathVariable("statusId") int id) {
        return new ResponseEntity(userFeedService.getRepliesFromStatus(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/status/reply/{replyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReply(@PathVariable("replyId") int replyId) {
        userFeedService.deleteReplyById(replyId);
    }
}
