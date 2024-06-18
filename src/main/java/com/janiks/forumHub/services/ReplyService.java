package com.janiks.forumHub.services;

import com.janiks.forumHub.domain.reply.Reply;
import com.janiks.forumHub.domain.topic.Topic;
import com.janiks.forumHub.dtos.ReplyCreationData;
import com.janiks.forumHub.dtos.ReplyData;
import com.janiks.forumHub.dtos.ReplyUpdate;
import com.janiks.forumHub.infra.exception.ValidationException;
import com.janiks.forumHub.infra.security.SecurityValidation;
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
    @Autowired
    private SecurityValidation securityValidation;


    public ReplyData create(Long topic_id, ReplyCreationData data, String token) {
        var user = securityValidation.getUser(token);
        var topic = getTopic(topic_id);
        var reply = new Reply(null, data.message(), LocalDateTime.now(), Boolean.FALSE, topic,user);
        this.replyRepository.save(reply);
        return new ReplyData(reply);
    }
    public ReplyData update(ReplyUpdate data) {
        var reply = replyRepository.getReferenceById(data.reply_id());
        reply.update(data);
        return new ReplyData(reply);
    }

    private Topic getTopic(Long topicId) {
        if(!this.topicRepository.existsById(topicId)){
            throw new ValidationException("Id do tópico informado não existe");
        }
        return this.topicRepository.getReferenceById(topicId);
    }


    public boolean delete(Long replyId, String token) {
        var user = replyRepository.findById(replyId).get().getUser();
        if(securityValidation.haveAuthorities(user, token)){
            this.replyRepository.deleteById(replyId);
            return true;
        }
        return false;
    }
}
