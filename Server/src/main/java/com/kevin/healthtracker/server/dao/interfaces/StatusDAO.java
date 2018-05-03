package com.kevin.healthtracker.server.dao.interfaces;

import java.util.List;

import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;


public interface StatusDAO {
    Status createStatus(Status status);

    Status getById(int id);

    List getStatusesByUser(User user, int pageNumber);

    void deleteById(int id);

}
