package com.janiks.forumHub.controllers;

import com.janiks.forumHub.domain.topic.Topic;
import com.janiks.forumHub.dtos.TopicCreationData;
import com.janiks.forumHub.dtos.TopicData;
import com.janiks.forumHub.dtos.TopicDataWithReplies;
import com.janiks.forumHub.dtos.TopicUpdate;
import com.janiks.forumHub.infra.security.SecurityValidation;
import com.janiks.forumHub.repositories.TopicRepository;
import com.janiks.forumHub.services.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SecurityValidation securityValidation;

    @PostMapping
    @Transactional
    public ResponseEntity createNewTopic(@RequestBody @Valid TopicCreationData data, HttpServletRequest request){
        var token = request.getHeader("Authorization").replace("Bearer ", "");
        var dto = topicService.create(data, token);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<TopicData>> getAllTopics(@PageableDefault(size= 10, sort = {"creation_date","course"}) Pageable pageable){
        var topics = this.topicRepository.findAll(pageable).map(TopicData::new);
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTopic(@PathVariable Long id){
        Optional<Topic> topic = this.topicRepository.findByIdWithCourse(id);
        if(topic.isPresent()){
            return ResponseEntity.ok().body(new TopicDataWithReplies(topic.get()));
        }
        return ResponseEntity.badRequest().body("Tópico não existe");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicData> editTopic(@RequestBody @Valid TopicUpdate data, @PathVariable Long id){
        var dto = topicService.update(data, id);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteTopic(@PathVariable Long id, HttpServletRequest request){
        var token = request.getHeader("Authorization").replace("Bearer ", "");
        if(this.securityValidation.haveAuthoritiesForTopic(id,token)){
            this.topicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
