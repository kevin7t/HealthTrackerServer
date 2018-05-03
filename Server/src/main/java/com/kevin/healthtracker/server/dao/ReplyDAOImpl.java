package com.kevin.healthtracker.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtracker.datamodels.Reply;
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
    public void deleteReply(Reply reply) {
        entityManager.remove(reply);
    }
}
