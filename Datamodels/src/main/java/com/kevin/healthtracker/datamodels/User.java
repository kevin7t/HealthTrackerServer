package com.kevin.healthtracker.datamodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String password;

}
