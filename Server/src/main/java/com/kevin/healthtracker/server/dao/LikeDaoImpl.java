package com.kevin.healthtracker.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.server.dao.interfaces.LikeDao;
import com.kevin.healthtracker.server.exception.DuplicateLikeException;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Slf4j
public class LikeDaoImpl implements LikeDao {

    @Autowired
    EntityManager entityManager;

    @Override
    public Like addLike(Like like) {
        try {
            entityManager.persist(like);
            entityManager.flush();
            return like;
        } catch (PersistenceException e) {
            throw new DuplicateLikeException(like.getUser().getUserName(), like.getStatus().getId());
        }
    }

    @Override
    public void removeLike(Like like) {
        entityManager.remove(like);
    }

    @Override
    public List<Like> getLikesFromStatus(Status status) {
        String query = "SELECT l FROM Like l WHERE l.status = ?";
        return entityManager.createQuery(query).setParameter(0, status).getResultList();
    }
}
