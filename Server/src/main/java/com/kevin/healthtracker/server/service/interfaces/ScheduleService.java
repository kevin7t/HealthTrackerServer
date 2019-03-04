package com.kevin.healthtracker.server.service.interfaces;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.Schedule;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.ScheduleDTO;

import java.text.ParseException;
import java.util.List;

public interface ScheduleService {
    Schedule addSchedule(ScheduleDTO schedule) throws ParseException;
    Schedule acceptSchedule(int scheduleId);
    Schedule declineSchedule(int scheduleId);
    List<Schedule> getAll(int user);
    List<Schedule> getInbound(int user);
    List<Schedule> getOutbound(int user);
}
