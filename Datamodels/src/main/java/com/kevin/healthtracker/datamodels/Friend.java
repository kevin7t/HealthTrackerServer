package com.kevin.healthtracker.datamodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.compositekeys.UserUserKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "friends")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(UserUserKey.class)
public class Friend {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @Id
    @ManyToOne
    private User user1;

    @Id
    @ManyToOne
    private User user2;

    @Column(name = "friendStatus", nullable = false)
    private RequestStatus friendStatus;

    //id of user initiating friend action
    @Column(name = "userActionId", nullable = false)
    private int userActionId;
}
