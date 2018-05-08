package com.kevin.healthtracker.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.server.dao.interfaces.ReplyDAO;
import lombok.extern.slf4j.Slf4j;


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
        String query = "SELECT r FROM replies r WHERE r.status = ?";
        return entityManager.createQuery(query).setParameter(0, status).getResultList();
    }

    @Override
    public void deleteReply(Reply reply) {
        entityManager.remove(reply);
    }
}
