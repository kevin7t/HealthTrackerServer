package com.kevin.healthtracker.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtracker.datamodels.Status;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Slf4j
public class StatusDAO implements IStatusDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Status createStatus(Status status) {
        entityManager.persist(status);
        entityManager.flush();
        return status;
    }

    @Override
    public Status getStatusById(int id) {
        return entityManager.find(Status.class,id);
    }

    @Override
    public List<Status> getStatusesByUserId(int userId) {
        String query = "SELECT s FROM status s WHERE s.user_id = ?";
        return entityManager.createQuery(query).setParameter(0,userId).getResultList();
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(getStatusById(id));
    }
}
