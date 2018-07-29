package com.kevin.healthtracker.datamodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    //Write only removes these fields from coming out of the controller
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "salt", nullable = false)
    private byte[] salt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "hash", nullable = false)
    private byte[] hash;

    //The reasoning password is not write only is because it is needed from the user
    //therefore it must be able to write and read from it
    @Transient
    private String password;

}
