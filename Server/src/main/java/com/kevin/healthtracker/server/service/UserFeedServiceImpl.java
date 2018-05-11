package com.kevin.healthtracker.server.service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.LikeDAOImpl;
import com.kevin.healthtracker.server.dao.ReplyDAOImpl;
import com.kevin.healthtracker.server.dao.StatusDAOImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.service.interfaces.UserFeedService;

@Service
public class UserFeedServiceImpl implements UserFeedService {

    @Autowired
    StatusDAOImpl statusDAO;

    @Autowired
    UserDAOImpl userDAO;

    @Autowired
    LikeDAOImpl likeDao;

    @Autowired
    ReplyDAOImpl replyDAO;


    @Override
    public Status createStatus(Status status) {
        status.setCreatedAt(currentTime());
        //Does not need to get entire user back
        return statusDAO.createStatus(status);
    }

    @Override
    public Status updateStatus(Status status) {
        return null;
    }

    @Override
    public List<Status> getStatusesByUserId(int userId, int pageNumber) {
        return statusDAO.getStatusesByUser(userDAO.getById(userId), pageNumber);
    }

    @Override
    public void deleteStatusById(int id) {
        statusDAO.deleteById(id);
    }

    @Override
    public Like addLikeToStatus(Like like) {
        like.setCreatedAt(currentTime());
        //Get whole object because status also has a user
        Status status = getStatusById(like.getStatus().getId());
        status.setLikeCount(status.getLikeCount() + 1);
        like.setStatus(status);
        like.setUser(getUserById(like.getUser().getId()));
        statusDAO.updateStatus(status);
        return likeDao.addLike(like);
    }

    @Override
    public List<Like> getLikesFromStatus(int id) {
        return likeDao.getLikesFromStatus(getStatusById(id));
    }

    @Override
    public void removeLikeFromStatus(int statusId, int userId) {
        //TODO: replace by creating the key using getuser/getstatus method
        likeDao.getLikesFromStatus(getStatusById(statusId)).forEach(like -> {
            if (like.getUser().getId() == (userId)) {
                likeDao.removeLike(like);
            }
        });
    }

    @Override
    public Reply addReplyToStatus(Reply reply) {
        reply.setCreatedAt(currentTime());
        reply.setStatus(getStatusById(reply.getStatus().getId()));
        reply.setUser(getUserById(reply.getUser().getId()));
        return replyDAO.createReply(reply);
    }

    @Override
    public List<Reply> getRepliesFromStatus(int id) {
        return replyDAO.getRepliesFromStatus(getStatusById(id));
    }

    @Override
    public void deleteReplyById(int id) {
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
