package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.interfaces.StatusDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
@Slf4j
public class StatusDAOImpl implements StatusDAO {
    private int pageSize = 20;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Status createStatus(Status status) {
        entityManager.persist(status);
        entityManager.flush();
        return status;
    }

    @Override
    public Status updateStatus(Status status) {
        return entityManager.merge(status);
    }

    @Override
    public Status getById(int id) {
        return entityManager.find(Status.class, id);
    }

    @Override
    public List<Status> getStatusesByUser(User user, int pageNumber) {
        String query = "SELECT s FROM Status s WHERE s.user = ?0";
        return entityManager.createQuery(query)
                .setParameter(0, user)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Status> getFriendStatusForFeed(User user, int pageNumber) {
        //Get entire friends by searching both ends of the relation
        String query = ("SELECT f.user2.id FROM Friend f WHERE f.user1 = ?0");
        List<Integer> friendIds = entityManager.createQuery(query).setParameter(0, user).getResultList();
        String query2 = ("SELECT f.user1.id FROM Friend f WHERE f.user2 = ?0");
        friendIds.addAll(entityManager.createQuery(query2).setParameter(0, user).getResultList());

        //Add the users own id to get their statuses
        friendIds.add(user.getId());

        String query3 = ("SELECT s FROM Status s WHERE s.user.id IN :ids ORDER BY s.createdAt DESC");
        List<Status> statusList = entityManager.createQuery(query3)
                .setParameter("ids", friendIds)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        return statusList;
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(getById(id));
    }
}
