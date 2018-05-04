package com.kevin.healthtracker.datamodels;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friends")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(UserUserKey.class)
public class Friend {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private User user1;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private User user2;

    @Column(name = "friendStatus", nullable = false)
    private int friendStatus;

    //id of user initiating friend action
    @Column(name = "userActionId", nullable = false)
    private int userActionId;
}
