package com.kevin.healthtracker.datamodels.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.StatusType;
import com.kevin.healthtracker.datamodels.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDTO {
    private Integer id;
    private Integer userId;
    private Date createdAt;
    private StatusType type;
    private String content;
    private int likeCount;
    private int replyCount;

    public StatusDTO(Status status) {
        id = status.getId();
        userId = status.getUser().getId();
        createdAt = status.getCreatedAt();
        type = status.getType();
        content = status.getContent();
        likeCount = status.getLikeCount();
        replyCount = status.getReplyCount();
    }

    public Status toEntity() {
        Status status = new Status();
        User user = new User();
        user.setId(this.getUserId());
        status.setId(this.getId());
        status.setContent(this.getContent());
        status.setCreatedAt(this.getCreatedAt());
        status.setType(this.getType());
        status.setUser(user);
        status.setLikeCount(this.getLikeCount());
        status.setReplyCount(this.getReplyCount());
        return status;
    }

}
