package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import com.kevin.healthtracker.server.dao.interfaces.FriendDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
@Slf4j
public class FriendDaoImpl implements FriendDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Friend addFriendRelation(Friend friend) {
        entityManager.persist(entityManager.merge(friend));
        return friend;
    }

    @Override
    public Friend updateFriendRelation(Friend friend) {
        return entityManager.merge(friend);
    }

    @Override
    public void deleteFriendRelation(Friend friend) {
        entityManager.remove(friend);
    }

    @Override
    public void deleteFriendRelation(User user1, User user2) {
        String query = ("DELETE FROM Friend f WHERE f.user1 = ? AND f.user2 = ?");
        entityManager.createQuery(query).setParameter(0, user1).setParameter(1, user2).executeUpdate();
    }

    @Override
    public Friend getFriendRelation(UserUserKey key) {
        return entityManager.find(Friend.class, key);
    }

    @Override
    public List<Friend> getFriendRelationList(User user) {
        String query = ("SELECT f FROM Friend f WHERE f.user1 = ?0");
        return entityManager.createQuery(query).setParameter(0, user).getResultList();
    }

    @Override
    public List<Friend> getUser2Relations(User user) {
        String query = ("SELECT f FROM Friend f WHERE f.user2 = ?");
        return entityManager.createQuery(query).setParameter(0, user).getResultList();
    }

    @Override
    public List<Friend> getFriendActivityByUserActionId(int userActionId) {
        String query = ("SELECT f FROM Friend f WHERE f.userActionId = ?");
        return entityManager.createQuery(query).setParameter(0, userActionId).getResultList();
    }
}
