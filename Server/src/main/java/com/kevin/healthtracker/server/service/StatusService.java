package com.kevin.healthtracker.server.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.server.dao.StatusDAO;

@Service
public class StatusService implements IStatusService {

    @Autowired
    StatusDAO statusDAO;

//    @Autowired
//    LikeDAO likeDAO;
//
//    @Autowired
//    ReplyDao replyDao;

    @Override
    public Status createStatus(Status status) {
       status.setCreatedAt(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
       return statusDAO.createStatus(status);
    }

    @Override
    public Status getStatusById(int id) {
        return statusDAO.getStatusById(id);
    }

    @Override
    public List<Status> getStatusesByUserId(int userId) {
        return statusDAO.getStatusesByUserId(userId);
    }

    @Override
    public List<Reply> getRepliesFromStatus(int id) {
        //use like dao to search like table to match status id
        return null;
    }

    @Override
    public List<Like> getLikesFromStatus(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {
        statusDAO.deleteById(id);
    }
}
