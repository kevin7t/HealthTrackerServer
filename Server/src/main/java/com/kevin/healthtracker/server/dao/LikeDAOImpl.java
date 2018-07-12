package com.kevin.healthtracker.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.interfaces.LikeDAO;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Slf4j
public class LikeDAOImpl implements LikeDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Like addLike(Like like) {
        try {
            entityManager.persist(like);
            entityManager.flush();
            return like;
        } catch (PersistenceException e) {
//            throw new DuplicateLikeException(like.getUser().getUserName(), like.getStatus().getId());
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void removeLike(Like like) {
        entityManager.remove(like);
    }

    @Override
    public void removeLike(User user, Status status) {
        String query = ("DELETE l FROM Like l WHERE l.user = ? AND l.status = ?");
        entityManager.createQuery(query).setParameter(0, user).setParameter(1, status).executeUpdate();
    }

    @Override
    public List<Like> getLikesFromStatus(Status status) {
        String query = "SELECT l FROM Like l WHERE l.status = ?0";
        return entityManager.createQuery(query).setParameter(0, status).getResultList();
    }
}
