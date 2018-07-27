package com.kevin.healthtracker.datamodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kevin.healthtracker.datamodels.compositekeys.UserStatusKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@IdClass(UserStatusKey.class)
@EqualsAndHashCode
//Use a composite key called UserStatusKey so that status and user is a composite primary key only allowing one like
public class Like {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;

    @Id
    @ManyToOne
    private User user;

    public String toString() {
        return "Like:[User_id: " + user.getId() + ",CreatedAt: " + createdAt + "]";
    }

}
