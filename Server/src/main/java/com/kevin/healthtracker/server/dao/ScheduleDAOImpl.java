package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.Schedule;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.interfaces.ScheduleDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Transactional
@Repository
@Slf4j
public class ScheduleDAOImpl implements ScheduleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Schedule addSchedule(Schedule schedule) {
        if (schedule.getUser1().getId() > schedule.getUser2().getId()) {
            User temp = schedule.getUser1();
            schedule.setUser1(schedule.getUser2());
            schedule.setUser2(temp);
        }
        entityManager.persist(schedule);
        return schedule;
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) {
        return entityManager.merge(schedule);
    }

    @Override
    public List<Schedule> getScheduleList(User user) {
        String query = ("SELECT s FROM Schedule s where s.user1 = ?0");
        List<Schedule> scheduleList = entityManager.createQuery(query).setParameter(0, user).getResultList();
        String query2 = ("SELECT s FROM Schedule s where s.user2 = ?0");
        scheduleList.addAll(entityManager.createQuery(query2).setParameter(0, user).getResultList());
        return scheduleList;
    }

    @Override
    public List<Schedule> getIncoming(User user) {
        String query = ("SELECT s FROM Schedule s WHERE s.userActionId <> ?0 " +
                "AND (s.user1 = ?0 OR s.user2 = ?0)" +
                "AND s.scheduleStatus = 'PENDING'");
        return entityManager.createQuery(query).setParameter(0, user.getId()).getResultList();
    }

    @Override
    public List<Schedule> getOutgoing(int userActionId) {
        String query = ("SELECT s FROM Schedule s WHERE s.userActionId = ?0");
        return entityManager.createQuery(query).setParameter(0, userActionId).getResultList();
    }

    public Schedule getById(int id){
        return entityManager.find(Schedule.class, id);
    }
}
