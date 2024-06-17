package com.janiks.forumHub.controllers;

import com.janiks.forumHub.dtos.TopicCreationData;
import com.janiks.forumHub.dtos.TopicData;
import com.janiks.forumHub.dtos.TopicUpdate;
import com.janiks.forumHub.repositories.TopicRepository;
import com.janiks.forumHub.services.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity createNewTopic(@RequestBody @Valid TopicCreationData data){
        var dto = topicService.create(data);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<TopicData>> getAllTopics(@PageableDefault(size= 1, sort = {"course","title"}) Pageable pageable){
        var topics = this.topicRepository.findAll(pageable).map(TopicData::new);
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTopic(@PathVariable Long id){
        var topic = this.topicRepository.findById(id);
        if(topic.isPresent()){
            return ResponseEntity.ok().body(new TopicData(topic.get()));
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
    public ResponseEntity deleteTopic(@PathVariable Long id){
        topicRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
