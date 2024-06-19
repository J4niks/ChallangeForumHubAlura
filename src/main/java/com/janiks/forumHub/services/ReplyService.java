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

    public ReplyData update(ReplyUpdate data , Long topicId, String token) {
        var reply = replyRepository.getReferenceById(data.reply_id());
        if(this.securityValidation.haveAuthorities(reply.getUser(),token)){
            if(data.soluction() != null) {
                reply.update(data, setAllReplyToFalse(topicId));
            }
            reply.update(data, false);
        }
        return new ReplyData(reply);
    }

    private boolean setAllReplyToFalse(Long topicId) {
        var topic = this.topicRepository.getReferenceById(topicId);
        var replies = replyRepository.findAllFromTopic(topic.getId());
        try{
            for(Reply reply : replies){
                reply.setSoluction(false);
            }
            return true;
        }catch (Exception ex){
            throw new ValidationException(ex.getMessage());
        }
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

    private boolean isItBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
