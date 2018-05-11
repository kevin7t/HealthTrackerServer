package com.kevin.healthtracker.server.dao.interfaces;

import java.util.List;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Status;

public interface LikeDAO {
    Like addLike(Like like);

    List<Like> getLikesFromStatus(Status status);

    void removeLike(Like like);

}
