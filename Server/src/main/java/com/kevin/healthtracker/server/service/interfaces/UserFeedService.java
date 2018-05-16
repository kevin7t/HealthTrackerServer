package com.kevin.healthtracker.server.service.interfaces;


import java.util.List;

import com.kevin.healthtracker.datamodels.dto.LikeDTO;
import com.kevin.healthtracker.datamodels.dto.ReplyDTO;
import com.kevin.healthtracker.datamodels.dto.StatusDTO;

public interface UserFeedService {

    StatusDTO createStatus(StatusDTO statusDTO);

    StatusDTO updateStatus(StatusDTO statusDTO);

    List<StatusDTO> getStatusesByUserId(int userId, int pageNumber);

    List<ReplyDTO> getRepliesFromStatus(int id);

    List<LikeDTO> getLikesFromStatus(int id);

    LikeDTO addLikeToStatus(LikeDTO likeDTO);

    ReplyDTO addReplyToStatus(ReplyDTO replyDTO);

    void deleteReplyById(int replyId);

    void removeLikeFromStatus(int statusId, int userId);

    void deleteStatusById(int id);

}
