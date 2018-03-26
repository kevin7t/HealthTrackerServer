package com.kevin.healthtracker.datamodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//TODO may cause issues deserializing null values
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

    @Transient
    private String password;

}
