package com.kevin.healthtracker.server.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import com.kevin.healthtracker.server.dao.interfaces.FriendDao;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository
@Slf4j
public class FriendDaoImpl implements FriendDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Friend addFriendRelation(Friend friend) {
        entityManager.persist(friend);
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
    public Friend getFriendRelation(UserUserKey key) {
        return entityManager.find(Friend.class, key);
    }

    @Override
    public List<Friend> getFriendRelationList(User user) {
        String query = ("SELECT f FROM Friend f WHERE f.user1 = ?0");
        return entityManager.createQuery(query).setParameter(0, user).getResultList();
    }
}
