package com.kevin.healthtracker.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.interfaces.StatusDAO;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Slf4j
public class StatusDAOImpl implements StatusDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Status createStatus(Status status) {
        entityManager.persist(status);
        entityManager.flush();
        return status;
    }

    @Override
    public Status getById(int id) {
        return entityManager.find(Status.class, id);
    }

    @Override
    public List<Status> getStatusesByUser(User user) {
        String query = "SELECT s FROM Status s WHERE s.user = ?";
        return entityManager.createQuery(query).setParameter(0, user).getResultList();
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(getById(id));
    }
}
