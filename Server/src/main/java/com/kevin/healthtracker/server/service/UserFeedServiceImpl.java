package com.kevin.healthtracker.server.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.LikeDTO;
import com.kevin.healthtracker.datamodels.dto.StatusDTO;
import com.kevin.healthtracker.server.dao.LikeDaoImpl;
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
    LikeDaoImpl likeDao;


    @Override
    public Status createStatus(Status status) {
        status.setCreatedAt(currentTime());
        return statusDAO.createStatus(status);
    }

    @Override
    public Status updateStatus(Status status) {
        return null;
    }

    @Override
    public List<StatusDTO> getStatusesByUserId(int userId, int pageNumber) {
        List<StatusDTO> statusList = new ArrayList<>();
        statusDAO.getStatusesByUser(userDAO.getById(userId), pageNumber)
                .forEach(status -> statusList.add(new StatusDTO(status)));
        return statusList;
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
    public void addLikeToStatus(int statusId, int userId) {
        Like like = new Like();
        like.setCreatedAt(currentTime());
        like.setStatus(getStatusById(statusId));
        like.setUser(getUserById(userId));
        likeDao.addLike(like);
    }

    @Override
    public List<LikeDTO> getLikesFromStatus(int id) {
        List<LikeDTO> likesList = new ArrayList<>();
        likeDao.getLikesFromStatus(getStatusById(id)).forEach(like -> likesList.add(new LikeDTO(like)));
        return likesList;
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
