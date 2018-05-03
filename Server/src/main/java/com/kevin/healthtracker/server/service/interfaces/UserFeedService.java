package com.kevin.healthtracker.server.service.interfaces;


import java.util.List;

import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.dto.LikeDTO;
import com.kevin.healthtracker.datamodels.dto.StatusDTO;

public interface UserFeedService {

    Status createStatus(Status status);

    Status updateStatus(Status status);

    List<StatusDTO> getStatusesByUserId(int userId, int pageNumber);

    List<Reply> getRepliesFromStatus(int id);

    List<LikeDTO> getLikesFromStatus(int id);

    void addLikeToStatus(int statusId, int userId);

    void removeLikeFromStatus(int statusId, int userId);

    void deleteStatusById(int id);

}
