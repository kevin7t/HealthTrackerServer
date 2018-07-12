package com.kevin.healthtracker.datamodels;

import javax.persistence.*;

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
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private User user1;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private User user2;

    @Column(name = "friendStatus", nullable = false)
    private FriendStatus friendStatus;

    //id of user initiating friend action
    @Column(name = "userActionId", nullable = false)
    private int userActionId;
}
