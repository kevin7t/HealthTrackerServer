package com.kevin.healthtrackerserver.datamodels;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Entity
@Table(name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @Column(name = "salt", nullable = false)
    private byte[] salt;

    @Column(name = "hash", nullable = false)
    private byte[] hash;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private String password;

}
