package com.kevin.healthtracker.server.service.interfaces;


import java.util.List;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.UserDTO;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO createUser(User user);

    UserDTO updateUser(User user);

    UserDTO findById(int id);

    Boolean authenticateUser(User user);

    void deleteById(int id);

}
