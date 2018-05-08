package com.kevin.healthtracker.datamodels.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyDTO {
    private int id;
    private Date createdAt;
    private int statusId;
    private int userId;
    private String content;

    public ReplyDTO(Reply reply) {
        id = reply.getId();
        createdAt = reply.getCreatedAt();
        statusId = reply.getStatus().getId();
        userId = reply.getUser().getId();
        content = reply.getContent();
    }

    public Reply toEntity() {
        Reply reply = new Reply();
        Status status = new Status();
        User user = new User();
        status.setId(this.statusId);
        user.setId(this.userId);
        reply.setId(this.id);
        reply.setCreatedAt(this.createdAt);
        reply.setContent(this.content);
        reply.setStatus(status);
        reply.setUser(user);
        return reply;
    }

}
