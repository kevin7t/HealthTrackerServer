package com.kevin.healthtracker.datamodels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDTO {
    private int id;
    private int userId;
    private int statusId;

    public LikeDTO(Like like) {
        id = like.getId();
        userId = like.getUser().getId();
        statusId = like.getStatus().getId();
    }

    public Like toEntity() {
        Like like = new Like();
        Status status = new Status();
        User user = new User();
        status.setId(this.statusId);
        user.setId(this.userId);
        like.setStatus(status);
        like.setUser(user);
        return like;
    }
}
