package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.Like;
import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.server.dao.interfaces.LikeDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Transactional
@Repository
@Slf4j
public class LikeDAOImpl implements LikeDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Like addLike(Like like) {
        try {
            //Merge used as it would have to check if it already exists in this case, there can only be one like per status from a user
            entityManager.merge(like);
            entityManager.flush();
            return like;
        } catch (PersistenceException e) {
//            throw new DuplicateLikeException(like.getUser().getUserName(), like.getStatus().getId());
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public void removeLike(Like like) {
        entityManager.remove(entityManager.merge(like));
    }

    @Override
    public void removeLike(User user, Status status) {
        String query = ("DELETE FROM Like l WHERE l.user = ?0 AND l.status = ?1");
        entityManager.createQuery(query).setParameter(0, user).setParameter(1, status).executeUpdate();
    }

    @Override
    public List<Like> getLikesFromStatus(Status status) {
        String query = "SELECT l FROM Like l WHERE l.status = ?0";
        return entityManager.createQuery(query).setParameter(0, status).getResultList();
    }


    public Like getLike(User user, Status status) {
        String query = ("SELECT l FROM Like l WHERE l.user = ?0 AND l.status = ?1");
        Like like = null;
        try {
            like = (Like) entityManager.createQuery(query).setParameter(0, user).setParameter(1, status).getSingleResult();
        }catch ( NoResultException e){
        }
        return like;
    }
}
