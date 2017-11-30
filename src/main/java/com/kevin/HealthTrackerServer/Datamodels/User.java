package com.kevin.HealthTrackerServer.Datamodels;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import org.hibernate.annotations.Type;

import lombok.Data;

@Entity
@Table (name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column (name = "id" , nullable = false)
    private int id;

    @Column (name = "user_id",nullable = false)
    private String userId;

    @Column (name = "name",nullable = false)
    private String name;

    @Column (name = "password",nullable = false)
    private String password;
}
