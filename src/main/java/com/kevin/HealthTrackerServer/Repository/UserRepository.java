package com.kevin.HealthTrackerServer.Repository;

import com.kevin.HealthTrackerServer.Datamodels.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUserId(@Param("userId") String userId);

    User findByUserName(@Param("userName") String name);

    @Transactional
    void deleteByUserId(@Param("userId") String userId);
}
