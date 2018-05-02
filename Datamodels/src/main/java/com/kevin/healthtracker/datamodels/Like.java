package com.kevin.healthtracker.datamodels;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.compositekeys.UserStatusKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(UserStatusKey.class)
//Use a composite key called UserStatusKey so that status and user is a composite primary key only allowing one like
public class Like {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Status status;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
