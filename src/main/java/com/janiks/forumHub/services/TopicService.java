package com.janiks.forumHub.services;

import com.janiks.forumHub.domain.course.Course;
import com.janiks.forumHub.domain.topic.Status;
import com.janiks.forumHub.domain.topic.Topic;
import com.janiks.forumHub.dtos.TopicCreationData;
import com.janiks.forumHub.dtos.TopicData;
import com.janiks.forumHub.dtos.TopicUpdate;
import com.janiks.forumHub.infra.exception.ValidationException;
import com.janiks.forumHub.infra.security.SecurityValidation;
import com.janiks.forumHub.infra.security.TokenService;
import com.janiks.forumHub.repositories.CourseRepository;
import com.janiks.forumHub.repositories.TopicRepository;
import com.janiks.forumHub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityValidation securityValidation;

    public TopicData create(TopicCreationData data, String token) {
        checkForSimilarTopics(data.title(), data.message());
        var course = getCourse(data.course_id());
        var user = this.securityValidation.getUser(token);

        var topic = new Topic(data.title(), data.message(), LocalDateTime.now(), Status.ATIVO, course, user);
        this.topicRepository.save(topic);
        return new TopicData(topic);
    }

    public TopicData update(Long id, String token, TopicUpdate data){
        var topic = this.topicRepository.getReferenceById(id);
        var existTitle = this.topicRepository.existsSimilarTitle(data.title());
        var existsMessage = this.topicRepository.existsSimilarMessage(data.message());
        if(this.securityValidation.haveAuthorities(topic.getUser(), token)){
            if(isItBlank(data.title()) && !existTitle){
                topic.setTitle(data.title());
            }
            if(existTitle){
                throw new ValidationException("Um tópico com esse titulo já existe");
            }
            if(isItBlank(data.message()) && !existsMessage){
                topic.setMessage(data.message());
            }
            if(existsMessage){
                throw new ValidationException("Um tópico com essa mensagem já existe");
            }
            if(isItBlank(String.valueOf(data.course_id()))){
                topic.setCourse(this.courseRepository.getReferenceById(data.course_id()));
            }
            if(isItBlank(String.valueOf(data.status()))){
                topic.setStatus(data.status());
            }
        }
        return new TopicData(topic);
    }

    public Course getCourse(Long id){
        if(!this.courseRepository.existsById(id)){
            throw new ValidationException("Id do curso informado não existe");
        }
        return this.courseRepository.getReferenceById(id);
    }

    private boolean isItBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private void checkForSimilarTopics(String title, String message){
        if(this.topicRepository.existsSimilarTitle(title) || this.topicRepository.existsSimilarMessage(message)){
            throw new ValidationException("O titulo e/ou mensagem já existem");
        }
    }
}
