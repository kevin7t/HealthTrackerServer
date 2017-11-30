package com.kevin.HealthTrackerServer.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kevin.HealthTrackerServer.Datamodels.User;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByUserId(@Param("userId") String userId);

    User findByName(@Param("name") String name);

    @Transactional
    void deleteByUserId(@Param("userId") String userId);
}
