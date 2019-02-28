package com.kevin.healthtracker.server.service.interfaces;

import com.kevin.healthtracker.datamodels.RequestStatus;
import com.kevin.healthtracker.datamodels.Schedule;
import com.kevin.healthtracker.server.dao.ScheduleDAOImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.dao.interfaces.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleDAOImpl scheduleDAO;

    @Autowired
    UserDAOImpl userDAO;

    @Override
    public Schedule addSchedule(Schedule schedule) {
        schedule.setUser1(userDAO.getById(schedule.getUser1().getId()));
        schedule.setUser2(userDAO.getById(schedule.getUser2().getId()));
        return scheduleDAO.addSchedule(schedule);
    }

    @Override
    public Schedule acceptSchedule(int scheduleId) {
        Schedule schedule = scheduleDAO.getById(scheduleId);
        schedule.setScheduleStatus(RequestStatus.ACCEPTED);
        return scheduleDAO.updateSchedule(schedule);
    }

    @Override
    public Schedule declineSchedule(int scheduleId) {
        Schedule schedule = scheduleDAO.getById(scheduleId);
        schedule.setScheduleStatus(RequestStatus.DECLINED);
        return scheduleDAO.updateSchedule(schedule);
    }

    @Override
    public List<Schedule> getAll(int user) {
        return scheduleDAO.getScheduleList(userDAO.getById(user));
    }

    @Override
    public List<Schedule> getInbound(int user) {
        return scheduleDAO.getIncoming(userDAO.getById(user));
    }

    @Override
    public List<Schedule> getOutbound(int user) {
        return scheduleDAO.getOutgoing(user);
    }
}
