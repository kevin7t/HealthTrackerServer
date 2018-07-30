package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.interfaces.StatusDAO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
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
    public Status getStatusById(int id) {
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

    @Override
    public void deleteStatusById(int id) {
        entityManager.remove(getStatusById(id));
    }

    public Status addLikeToStatus(Like like, Status status) {
        Status retrievedStatus = entityManager.find(Status.class, status.getId());
        Hibernate.initialize(retrievedStatus.getLikes());
        retrievedStatus.addLike(like);
        retrievedStatus.setLikeCount(retrievedStatus.getLikes().size());
        return updateStatus(retrievedStatus);
    }

    public List<Like> getLikesFromStatus(Status status) {
        Status retrievedStatus = entityManager.find(Status.class, status.getId());
        Hibernate.initialize(retrievedStatus.getLikes());
        return retrievedStatus.getLikes();
    }

    public void removeLikeFromStatus(User user, Status status) {
        status.setLikeCount(status.getLikeCount() - 1);
        updateStatus(status);

        String query = ("DELETE FROM Like l WHERE l.user = ?0 AND l.status = ?1");
        entityManager.createQuery(query).setParameter(0, user).setParameter(1, status).executeUpdate();

    }

    public Status addReplyToStatus(Reply reply, Status status) {
        Status retrievedStatus = entityManager.find(Status.class, status.getId());
        Hibernate.initialize(retrievedStatus.getReplies());
        retrievedStatus.addReply(reply);
        retrievedStatus.setReplyCount(retrievedStatus.getReplies().size());
        return updateStatus(retrievedStatus);
    }

    public List<Reply> getRepliesFromStatus(Status status) {
        Status retrievedStatus = entityManager.find(Status.class, status.getId());
        Hibernate.initialize(retrievedStatus.getReplies());
        return retrievedStatus.getReplies();
    }

    public Reply getReplyById(int id) {
        return entityManager.find(Reply.class, id);
    }

    public void deleteReplyFromStatus(Reply reply, Status status) {
        status.setReplyCount(status.getReplyCount() - 1);
        updateStatus(status);

        String query = ("DELETE FROM Reply r WHERE r.status = ?0 AND r.id = ?1");
        entityManager.createQuery(query).setParameter(0, status).setParameter(1, reply.getId()).executeUpdate();
    }

}
