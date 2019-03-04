package com.kevin.healthtracker.server.service.interfaces;

import com.kevin.healthtracker.datamodels.RequestStatus;
import com.kevin.healthtracker.datamodels.Schedule;
import com.kevin.healthtracker.datamodels.dto.ScheduleDTO;
import com.kevin.healthtracker.server.dao.ScheduleDAOImpl;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.dao.interfaces.UserDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleDAOImpl scheduleDAO;

    @Autowired
    UserDAOImpl userDAO;

    @Override
    public Schedule addSchedule(ScheduleDTO scheduleDTO) throws ParseException {
        Schedule schedule = mapDTO(scheduleDTO);
        return scheduleDAO.addSchedule(schedule);
    }

    private Schedule mapDTO(ScheduleDTO scheduleDTO) throws ParseException {
        Schedule schedule = new Schedule();
        schedule.setUser1(userDAO.getById(scheduleDTO.getUser1id()));
        schedule.setUser2(userDAO.getById(scheduleDTO.getUser2id()));
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = dateFormat.parse(scheduleDTO.getDateTime());
        schedule.setDateTime(new Timestamp(date.getTime()));
        schedule.setScheduleStatus(RequestStatus.valueOf(scheduleDTO.getScheduleStatus()));
        schedule.setUserActionId(scheduleDTO.getUserActionId());
        schedule.setUser1(userDAO.getById(schedule.getUser1().getId()));
        schedule.setUser2(userDAO.getById(schedule.getUser2().getId()));
        return schedule;
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
