package com.kevin.healthtracker.server.dao.interfaces;

import com.kevin.healthtracker.datamodels.Status;
import com.kevin.healthtracker.datamodels.User;

import java.util.List;


public interface StatusDAO {
    Status createStatus(Status status);

    Status updateStatus(Status status);

    Status getStatusById(int id);

    List getStatusesByUser(User user, int pageNumber);

    void deleteStatusById(int id);

}
