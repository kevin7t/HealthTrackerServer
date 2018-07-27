package com.kevin.healthtracker.datamodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "statuses")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int Id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "type", nullable = false)
    private StatusType type;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "reply_count")
    private int replyCount;

    @OneToMany(
            mappedBy = "status",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Like> likes = new ArrayList<>();
    @OneToMany(
            mappedBy = "status",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Reply> replies = new ArrayList<>();

    public void addLike(Like like) {
        like.setStatus(this);
        likes.add(like);
    }

    public void removeLike(Like like) {
        like.setStatus(null);
        likes.remove(like);
    }

    public void addReply(Reply reply) {
        reply.setStatus(this);
        replies.add(reply);
    }

    public void removeReply(Reply reply) {
        reply.setStatus(null);
        replies.remove(reply);
    }

    public String toString() {
        return "Status:[User_id: " + user.getId() + ",CreatedAt: " + createdAt + ",Type: " + type + ",Content: " + content + "]";
    }
}
