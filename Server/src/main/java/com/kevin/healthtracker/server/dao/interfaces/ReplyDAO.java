package com.kevin.healthtracker.server.dao.interfaces;

import java.util.List;

import com.kevin.healthtracker.datamodels.Reply;
import com.kevin.healthtracker.datamodels.Status;

public interface ReplyDAO {
    Reply createReply(Reply reply);

    List<Reply> getRepliesFromStatus(Status status);

    Reply getById(int id);

    void deleteReply(Reply reply);
}
