package com.kevin.healthtracker.datamodels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyDTO {
    private int id;
    private String username;
    private Date createdAt;
    private int statusId;
    private int userId;
    private String content;
}
