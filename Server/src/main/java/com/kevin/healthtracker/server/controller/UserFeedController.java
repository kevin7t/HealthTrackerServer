package com.kevin.healthtracker.server.controller;

import java.util.ArrayList;
import java.util.List;

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
        return new ResponseEntity(userFeedService.createStatus(statusDTO.toEntity()), HttpStatus.CREATED);
    }

    //Needs ?page=pageNumber in url
    @RequestMapping(value = "/status/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getStatus(@PathVariable("userId") int userId, @RequestParam("page") int pageNumber) {
        List<StatusDTO> statusList = new ArrayList<>();
        userFeedService.getStatusesByUserId(userId, pageNumber)
                .forEach(status -> statusList.add(new StatusDTO(status)));
        return new ResponseEntity(statusList, HttpStatus.OK);
    }

    @RequestMapping(value = "/status/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStatus(@PathVariable("id") int id) {
        userFeedService.deleteStatusById(id);
    }

    @RequestMapping(value = "/status/like", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addLike(@RequestBody LikeDTO likeDTO) {
        LikeDTO response = new LikeDTO(userFeedService.addLikeToStatus(likeDTO.toEntity()));
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/status/like/{statusId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getLikes(@PathVariable("statusId") int id) {
        List<LikeDTO> likesList = new ArrayList<>();
        userFeedService.getLikesFromStatus(id).forEach(like -> likesList.add(new LikeDTO(like)));
        return new ResponseEntity(likesList, HttpStatus.OK);
    }

    @RequestMapping(value = "/status/like/{statusId}/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void removeLike(@PathVariable("statusId") int statusId, @PathVariable("userId") int userId) {
        userFeedService.removeLikeFromStatus(statusId, userId);
    }

    @RequestMapping(value = "/status/reply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity sendReply(@RequestBody ReplyDTO replyDTO) {
        ReplyDTO response = new ReplyDTO(userFeedService.addReplyToStatus(replyDTO.toEntity()));
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/status/reply/{statusId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getReplies(@PathVariable("statusId") int id) {
        List<ReplyDTO> replyList = new ArrayList<>();
        userFeedService.getRepliesFromStatus(id).forEach(reply -> replyList.add(new ReplyDTO(reply)));
        return new ResponseEntity(replyList, HttpStatus.OK);
    }

    @RequestMapping(value = "/status/reply/{replyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteReply(@PathVariable("replyId") int replyId) {
        userFeedService.deleteReplyById(replyId);
    }
}
