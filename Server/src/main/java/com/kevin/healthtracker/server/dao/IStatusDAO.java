package com.kevin.healthtracker.server.dao;

import java.util.List;

import com.kevin.healthtracker.datamodels.Status;


public interface IStatusDAO {
    Status createStatus(Status status);

    Status getStatusById(int id);

    List getStatusesByUserId(int userId);

    void deleteById(int id);

}
