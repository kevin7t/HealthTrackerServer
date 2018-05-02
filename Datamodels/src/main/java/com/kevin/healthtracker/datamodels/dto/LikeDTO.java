package com.kevin.healthtracker.datamodels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.Like;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDTO {
    private int id;
    private int userId;
    private String userName;
    private int statusId;

    public LikeDTO(Like like) {
        id = like.getId();
        userId = like.getUser().getId();
        userName = like.getUser().getUserName();
        statusId = like.getStatus().getId();
    }
}
