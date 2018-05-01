package com.kevin.healthtracker.server.service;


import java.util.List;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;

public interface IStatusService {
    Status createStatus(Status status);

    Status getStatusById(int id);

    List<Status> getStatusesByUserId(int userId);

    List<Reply> getRepliesFromStatus(int id);

    List<Like> getLikesFromStatus(int id);

    void deleteById(int id);

}
