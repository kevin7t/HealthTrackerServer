package com.kevin.healthtracker.datamodels.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.StatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDTO {
    private int id;
    private int userId;
    private Date createdAt;
    private StatusType type;
    private String content;
    private int likeCount;
    private int replyCount;
}
