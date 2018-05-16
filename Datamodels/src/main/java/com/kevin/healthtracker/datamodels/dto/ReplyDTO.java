package com.kevin.healthtracker.datamodels.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
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
}
