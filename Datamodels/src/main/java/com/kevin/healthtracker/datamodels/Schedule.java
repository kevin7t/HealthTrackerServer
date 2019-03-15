package com.kevin.healthtracker.datamodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @Column(name = "date", nullable = false)
    private Timestamp dateTime;

    @Column(name = "scheduleStatus", nullable = false)
    private RequestStatus scheduleStatus;

    //id of user initiating friend action
    @Column(name = "userActionId", nullable = false)
    private int userActionId;

    @Column(name = "content", nullable = false)
    private String content;
}
