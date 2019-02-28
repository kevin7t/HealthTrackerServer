package com.kevin.healthtracker.server.dao;

import com.kevin.healthtracker.datamodels.Friend;
import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import com.kevin.healthtracker.server.dao.interfaces.FriendDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
@Slf4j
public class FriendDAOImpl implements FriendDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Friend addFriendRelation(Friend friend) {

        if (!checkIfAlreadyExists(friend)) {
            //Swap user order for the database to keep user 1 id being < user 2
            if (friend.getUser1().getId() > friend.getUser2().getId()) {
                User temp = friend.getUser1();
                friend.setUser1(friend.getUser2());
                friend.setUser2(temp);
            }
            log.info("Friend does not exist, creating new friend");
            entityManager.persist(entityManager.merge(friend));
        } else {
            log.info("Friend already exists, no changes done");
        }
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
    @SuppressWarnings("unchecked")
    public List<Friend> getFriendRelationList(User user) {
        String query = ("SELECT f FROM Friend f WHERE f.user1 = ?0");
        List<Friend> friendList = entityManager.createQuery(query).setParameter(0, user).getResultList();
        String query2 = ("SELECT f FROM Friend f WHERE f.user2 = ?0");
        friendList.addAll(entityManager.createQuery(query2).setParameter(0, user).getResultList());
        return friendList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Friend> getIncomingOutcomingFriends(User user) {
        String query = ("SELECT f " +
                "FROM Friend f WHERE f.user1 = ?0");
        List<Friend> friendList = entityManager.createQuery(query).setParameter(0, user).getResultList();
        String query2 = ("SELECT f FROM Friend f WHERE f.user2 = ?0");
        friendList.addAll(entityManager.createQuery(query2).setParameter(0, user).getResultList());

        //CALCULATE THIS CLIENT SIDE  AND JUST RETURN THE ENTIRE FRIEND STUFF, CAN USE FOR BOTH OUTGOING AND INCOMING
        return friendList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Friend> getOutgoingRequestsFromUser(int userActionId) {
        String query = ("SELECT f FROM Friend f WHERE f.userActionId = ?0");
        return entityManager.createQuery(query).setParameter(0, userActionId).getResultList();
    }

    private boolean checkIfAlreadyExists(Friend friend) {
        Friend storedRelation = getFriendRelation(new UserUserKey(friend.getUser1(), friend.getUser2()));
        return storedRelation != null && storedRelation.getUser1().getId() == friend.getUser1().getId() &&
                storedRelation.getUser2().getId() == friend.getUser2().getId();

    }
}
