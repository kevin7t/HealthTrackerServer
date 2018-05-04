package com.kevin.healthtracker.server.service.interfaces;


import java.util.List;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;

public interface UserFeedService {

    Status createStatus(Status status);

    Status updateStatus(Status status);

    List<Status> getStatusesByUserId(int userId, int pageNumber);

    List<Reply> getRepliesFromStatus(int id);

    List<Like> getLikesFromStatus(int id);

    void addLikeToStatus(int statusId, int userId);

    Reply addReplyToStatus(int statusId, int userId, String content);

    void removeReplyFromStatus(int statusId, int userId, int replyId);

    void removeLikeFromStatus(int statusId, int userId);

    void deleteStatusById(int id);

}
