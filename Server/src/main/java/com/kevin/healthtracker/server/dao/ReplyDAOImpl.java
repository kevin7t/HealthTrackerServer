package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.server.dao.interfaces.ReplyDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Transactional
@Repository
@Slf4j
public class ReplyDAOImpl implements ReplyDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Reply createReply(Reply reply) {
        entityManager.persist(reply);
        entityManager.flush();
        return reply;
    }

    @Override
    public List<Reply> getRepliesFromStatus(Status status) {
        String query = "SELECT r FROM Reply r WHERE r.status = ?1";
        return entityManager.createQuery(query).setParameter(1, status).getResultList();
    }

    @Override
    public Reply getById(int id) {
        return entityManager.find(Reply.class, id);
    }

    @Override
    public void deleteReply(Reply reply) {
        entityManager.remove(entityManager.merge(reply));
    }
}
