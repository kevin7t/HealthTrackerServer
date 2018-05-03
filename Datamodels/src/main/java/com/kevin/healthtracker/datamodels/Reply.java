package com.kevin.healthtracker.datamodels;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "replies")
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "content", nullable = false)
    private String content;
}
