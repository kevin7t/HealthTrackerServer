package com.kevin.healthtracker.server.service;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.LikeDTO;
import com.kevin.healthtracker.datamodels.dto.ReplyDTO;
import com.kevin.healthtracker.datamodels.dto.StatusDTO;
import com.kevin.healthtracker.server.dao.ReplyDAOImpl;
import com.kevin.healthtracker.server.dao.StatusDAOImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.exception.DuplicateLikeException;
import com.kevin.healthtracker.server.service.interfaces.UserFeedService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFeedServiceImpl implements UserFeedService {

    @Autowired
    StatusDAOImpl statusDAO;

    @Autowired
    UserDAOImpl userDAO;

    @Autowired
    ReplyDAOImpl replyDAO;

    ModelMapper modelMapper = new ModelMapper();


    @Override
    public StatusDTO createStatus(StatusDTO statusDTO) {
        //Map DTO to Entity
        Status status = modelMapper.map(statusDTO, Status.class);
        status.setCreatedAt(currentTime());
        status = statusDAO.createStatus(status);
        //Map Entity back to DTO
        return modelMapper.map(status, StatusDTO.class);
    }

    @Override
    public List<StatusDTO> getStatusesByUserId(int userId, int pageNumber) {
        return statusDAO.getStatusesByUser(userDAO.getById(userId), pageNumber)
                .stream().map(status -> modelMapper.map(status, StatusDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStatusById(int id) {
        statusDAO.deleteById(id);
    }

    @Override
    public LikeDTO addLikeToStatus(LikeDTO likeDTO) {
        Like like = modelMapper.map(likeDTO, Like.class);
        like.setCreatedAt(currentTime());
        //Get whole object because status also has a user
        Status status = getStatusById(like.getStatus().getId());

        like.setStatus(status);
        like.setUser(getUserById(like.getUser().getId()));

        try {
            statusDAO.addLikeToStatus(like, status);
        } catch (DuplicateLikeException e) {
            e.printStackTrace();
        }
        return likeDTO;
    }

    @Override
    public List<LikeDTO> getLikesFromStatus(int id) {
        return statusDAO.getLikesFromStatus(getStatusById(id))
                .stream().map(like -> modelMapper.map(like, LikeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeLikeFromStatus(int userId, int statusId) {
        statusDAO.removeLikeFromStatus(getUserById(userId), getStatusById(statusId));
    }

    @Override
    public ReplyDTO addReplyToStatus(ReplyDTO replyDTO) {
        Reply reply = modelMapper.map(replyDTO, Reply.class);
        Status status = getStatusById(reply.getStatus().getId());
        status.setReplyCount(status.getReplyCount() + 1);
        reply.setCreatedAt(currentTime());
        reply.setStatus(getStatusById(reply.getStatus().getId()));
        reply.setUser(getUserById(reply.getUser().getId()));
        return modelMapper.map(replyDAO.createReply(reply), ReplyDTO.class);
    }

    @Override
    public List<ReplyDTO> getRepliesFromStatus(int id) {
        return replyDAO.getRepliesFromStatus(getStatusById(id))
                .stream().map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReplyById(int id) {
        Status status = getStatusById(getReplyById(id).getStatus().getId());
        status.setReplyCount(status.getReplyCount() - 1);
        replyDAO.deleteReply(getReplyById(id));
    }

    private Date currentTime() {
        return new Date(Calendar.getInstance().getTimeInMillis());
    }

    private Status getStatusById(int id) {
        return statusDAO.getById(id);
    }

    private User getUserById(int id) {
        return userDAO.getById(id);
    }

    private Reply getReplyById(int id) {
        return replyDAO.getById(id);
    }
}
