package com.kevin.healthtracker.server.service;

import static java.util.stream.Collectors.toList;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevin.healthtracker.datamodels.User;
import com.kevin.healthtracker.datamodels.dto.UserDTO;
import com.kevin.healthtracker.server.dao.UserDAOImpl;
import com.kevin.healthtracker.server.service.interfaces.UserService;
import com.kevin.healthtracker.server.util.Encrypter;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAOImpl userDAO;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.getAllUsers().stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(toList());
    }

    @Override
    public UserDTO createUser(User user) {
        return modelMapper.map(userDAO.createUser(user), UserDTO.class);
    }

    @Override
    public UserDTO updateUser(User user) {
        return modelMapper.map(userDAO.updateUser(user), UserDTO.class);
    }

    @Override
    public UserDTO findById(int id) {
        return modelMapper.map(userDAO.getById(id), UserDTO.class);
    }

    @Override
    public void deleteById(int id) {
        userDAO.deleteById(id);
    }

    @Override
    public Boolean authenticateUser(User user) {
        try {
            User userFromDb = userDAO.getByUserName(user.getUserName());
            return Encrypter.authenticate(user.getPassword(), userFromDb.getHash(), userFromDb.getSalt());
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }
}
