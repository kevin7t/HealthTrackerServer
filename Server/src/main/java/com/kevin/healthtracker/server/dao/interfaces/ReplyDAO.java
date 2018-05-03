package com.kevin.healthtracker.server.dao.interfaces;

import com.kevin.healthtracker.datamodels.Reply;

public interface ReplyDAO {
    Reply createReply(Reply reply);

    void deleteReply(Reply reply);
}
