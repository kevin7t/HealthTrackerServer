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
    public List<Reply> getRepliesFromStatus(int id) {
        //TODO implement replies
        return null;
    }

    @Override
    public void addLikeToStatus(Like like) {
        like.setCreatedAt(currentTime());
        //Get whole object because status also has a user
        like.setStatus(getStatusById(like.getStatus().getId()));
        like.setUser(getUserById(like.getUser().getId()));
        likeDao.addLike(like);
    }

    @Override
    public Reply addReplyToStatus(int statusId, int userId, String content) {
        return null;
    }

    @Override
    public void removeReplyFromStatus(int statusId, int userId, int replyId) {

    }

    @Override
    public List<Like> getLikesFromStatus(int id) {
        return likeDao.getLikesFromStatus(getStatusById(id));
    }

    @Override
    public void removeLikeFromStatus(int statusId, int userId) {
        likeDao.getLikesFromStatus(getStatusById(statusId)).forEach(like -> {
            if (like.getUser().getId().equals(userId)) {
                likeDao.removeLike(like);
            }
        });
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

}
