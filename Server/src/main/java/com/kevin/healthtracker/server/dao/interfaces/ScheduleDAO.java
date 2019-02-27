package com.kevin.healthtracker.server.dao.interfaces;

import com.kevin.healthtracker.datamodels.Schedule;
import com.kevin.healthtracker.datamodels.User;

import java.util.List;

public interface ScheduleDAO {
    Schedule addSchedule(Schedule schedule);

    Schedule updateSchedule(Schedule schedule);

    List<Schedule> getScheduleList(User user);

    List<Schedule> getIncoming(User user);

    List<Schedule> getOutgoing(int userId);

}
