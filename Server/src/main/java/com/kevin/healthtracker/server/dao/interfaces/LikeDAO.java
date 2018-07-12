package com.kevin.healthtracker.server.dao.interfaces;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;

import java.util.List;

public interface LikeDAO {
    Like addLike(Like like);

    List<Like> getLikesFromStatus(Status status);

    void removeLike(Like like);

    void removeLike(User user, Status status);


}
