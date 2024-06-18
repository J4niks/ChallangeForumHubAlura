package com.janiks.forumHub.services;

import com.janiks.forumHub.domain.course.Course;
import com.janiks.forumHub.domain.topic.Status;
import com.janiks.forumHub.domain.topic.Topic;
import com.janiks.forumHub.dtos.TopicCreationData;
import com.janiks.forumHub.dtos.TopicData;
import com.janiks.forumHub.dtos.TopicUpdate;
import com.janiks.forumHub.infra.exception.ValidationException;
import com.janiks.forumHub.repositories.CourseRepository;
import com.janiks.forumHub.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseRepository courseRepository;

    public TopicData create(TopicCreationData data) {
        if(this.topicRepository.existsSimilarTopicsByLevenshteinDistance(data.title(), data.message())){
            throw new ValidationException("Mensagem e/ou titulo já existem no banco de dados. Tópicos devem ser unicos!");
        }

        var course = getCourse(data.course_id());

        var topic = new Topic(null, data.title(), data.message(), LocalDateTime.now(), Status.ATIVO, course);
        this.topicRepository.save(topic);
        return new TopicData(topic);
    }

    public TopicData update(TopicUpdate data, Long topicId){
        var topic = this.topicRepository.getReferenceById(topicId);
        if(data.course_id() == null){
            topic.update(data, null);
        }else{
            topic.update(data, getCourse(data.course_id()));
        }
        return new TopicData(topic);
    }

    public Course getCourse(Long id){
        if(!this.courseRepository.existsById(id)){
            throw new ValidationException("Id do curso informado não existe");
        }
        return this.courseRepository.getReferenceById(id);
    }
}
