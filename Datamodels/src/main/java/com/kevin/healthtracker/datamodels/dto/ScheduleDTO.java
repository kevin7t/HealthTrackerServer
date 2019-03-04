package com.kevin.healthtracker.datamodels.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.StatusType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
    private int id;
    private int user1id;
    private int user2id;
    private String dateTime;
    private String scheduleStatus;
    private int userActionId;
}
