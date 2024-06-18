package com.janiks.forumHub.services;

import com.janiks.forumHub.domain.reply.Reply;
import com.janiks.forumHub.domain.topic.Topic;
import com.janiks.forumHub.dtos.ReplyCreationData;
import com.janiks.forumHub.dtos.ReplyData;
import com.janiks.forumHub.infra.exception.ValidationException;
import com.janiks.forumHub.repositories.ReplyRepository;
import com.janiks.forumHub.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private TopicRepository topicRepository;


    public ReplyData create(Long topic_id, ReplyCreationData data) {
        var topic = getTopic(topic_id);
        var reply = new Reply(null, data.message(), LocalDateTime.now(), Boolean.FALSE, topic);
        this.replyRepository.save(reply);
        return new ReplyData(reply);
    }

    private Topic getTopic(Long topicId) {
        if(!this.topicRepository.existsById(topicId)){
            throw new ValidationException("Id do tópico informado não existe");
        }
        return this.topicRepository.getReferenceById(topicId);
    }
}
