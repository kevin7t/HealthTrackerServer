package com.kevin.healthtracker.datamodels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.RequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
    private int id;
    private int user1id;
    private int user2id;
    private Timestamp dateTime;
    private RequestStatus scheduleStatus;
    private int userActionId;
    private String content;
}
