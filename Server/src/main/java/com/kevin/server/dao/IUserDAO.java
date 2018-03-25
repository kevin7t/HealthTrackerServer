package dao;

import java.util.List;

import com.kevin.server.datamodels.User;

public interface IUserDAO {
    List<User> getAllUsers();

    int createUser(User user);

    User updateUser(User user);

    User findById(int id);

    User findByUserName(String userName);

    void deleteById(int id);

}
