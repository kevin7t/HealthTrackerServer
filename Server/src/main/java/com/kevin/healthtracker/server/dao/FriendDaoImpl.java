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
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
@Slf4j
public class FriendDaoImpl implements FriendDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Friend addFriendRelation(Friend friend) {
        //TODO Issue here is that using merge avoids detatched entity issues, but allows it to re-write the relationship from user2, will have to check if it already exists before calling persist
        entityManager.persist(entityManager.merge(friend));
        return friend;
    }

    @Override
    public Friend updateFriendRelation(Friend friend) {
        return entityManager.merge(friend);
    }

    @Override
    public void deleteFriendRelation(Friend friend) {
        entityManager.remove(entityManager.merge(friend));
    }

    @Override
    public Friend getFriendRelation(UserUserKey key) {
        return entityManager.find(Friend.class, key);
    }

    @Override
    public List<Friend> getFriendRelationList(User user) {
        String query = ("SELECT f FROM Friend f WHERE f.user1 = ?0");
        List<Friend> sentList = entityManager.createQuery(query).setParameter(0, user).getResultList();
        query = ("SELECT f FROM Friend f WHERE f.user2 = ?0");
        List<Friend> receivedList = entityManager.createQuery(query).setParameter(0, user).getResultList();
        List<Friend> combinedList = new ArrayList<>();
        combinedList.addAll(sentList);
        combinedList.addAll(receivedList);
        return combinedList;
    }

    @Override
    public List<Friend> getReceivedFriendRequestsForUser(User user) {
        String query = ("SELECT f FROM Friend f WHERE f.user2 = ?0");
        return entityManager.createQuery(query).setParameter(0, user).getResultList();
    }

    @Override
    public List<Friend> getFriendActivityByUserActionId(int userActionId) {
        String query = ("SELECT f FROM Friend f WHERE f.userActionId = ?0");
        return entityManager.createQuery(query).setParameter(0, userActionId).getResultList();
    }
}
